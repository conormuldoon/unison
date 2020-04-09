package eu.acclimatize.unison.harvester;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.acclimatize.unison.Cloud;
import eu.acclimatize.unison.HourlyPrecipitation;
import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.HourlyWeather;
import eu.acclimatize.unison.HourlyWeatherRepository;
import eu.acclimatize.unison.ItemKey;
import eu.acclimatize.unison.PrecipitationValue;
import eu.acclimatize.unison.WeatherValue;
import eu.acclimatize.unison.WindDirection;
import eu.acclimatize.unison.WindSpeed;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.LocationRepository;

/**
 * A service that harvest data from a HARMONIE-AROME API.
 */
@Service
public class HarvesterService {

	private static final int SLEEP_TIME = 60000;

	private static final String VALUE = "value";
	private static final String PERCENT = "percent";

	private LocationRepository locationRepository;

	private HourlyPrecipitationRepository precipitationRepository;
	private HourlyWeatherRepository weatherRepository;
	private DocumentRequestService drs;

	private List<HourlyPrecipitation> precipitation;
	private List<HourlyWeather> weather;

	private Logger logger;

	private SimpleDateFormat dateFormat;

	private Executor executor;

	/**
	 * Creates an instance of HarvesterService.
	 * 
	 * @param locationRepository      The repository that stores the locations that
	 *                                data will be harvested for.
	 * @param precipitationRepository The repository where precipitation data is
	 *                                stored.
	 * @param weatherRepository       The repository where non-precipitation weather
	 *                                data is stored.
	 * @param drs                     The service that is used to obtain XML
	 *                                documents from the API.
	 * @param logger                  Logs warning messages and exceptions.
	 * @param simpleDateFormat        Used to parse date data using a given time
	 *                                zone.
	 * @param stExcecutor             Used to execute the data harvesting process on
	 *                                a thread.
	 */
	public HarvesterService(LocationRepository locationRepository,
			HourlyPrecipitationRepository precipitationRepository, HourlyWeatherRepository weatherRepository,
			DocumentRequestService drs, Logger logger, SimpleDateFormat simpleDateFormat, Executor executor) {

		this.locationRepository = locationRepository;
		this.weatherRepository = weatherRepository;
		this.precipitationRepository = precipitationRepository;
		this.drs = drs;

		precipitation = new ArrayList<>();
		weather = new ArrayList<>();
		this.logger = logger;

		this.dateFormat = simpleDateFormat;
		this.executor = executor;

	}

	/**
	 * Requests and data stores from a HARMONIE-AROME API for locations in the
	 * database.
	 * 
	 * @throws InterruptedException The service will sleep for a second if it is
	 *                              having problem obtaining data from the API. The
	 *                              exception will be thrown if the service is
	 *                              interrupted during this time.
	 */
	@Transactional
	public synchronized void harvestData() throws InterruptedException {

		harvestData(locationRepository.findAll());

		store(precipitation, weather);
		precipitation.clear();
		weather.clear();

	}

	private void harvestData(Iterable<? extends LocationDetails> iterable) throws InterruptedException {
		executor.execute(() -> {
			for (LocationDetails loc : iterable) {

				try {

					// If data is not received from the API due to a connection error, sleeps and
					// then attempts to connect
					// again.
					while (!processLocation(loc, precipitation, weather)) {
						Thread.sleep(SLEEP_TIME);
					}
				} catch (DocumentRequestException | InterruptedException e) {

					logger.log(Level.SEVERE, e.getMessage());
				}

			}
		});

	}

	/**
	 * Requests and stores data from a HARMONIE-AROME API for a given location.
	 * 
	 * @param location The location to obtain data for.
	 * @return True if the data was received and stored, false otherwise.
	 * @throws DocumentRequestException Thrown when the generated XML for the
	 *                                  location was not found.
	 */
	public synchronized boolean processLocation(LocationDetails location) throws DocumentRequestException {

		List<HourlyPrecipitation> hourlyPrecipitation = new ArrayList<>();
		List<HourlyWeather> hourlyWeather = new ArrayList<>();
		if (processLocation(location, hourlyPrecipitation, hourlyWeather)) {
			store(hourlyPrecipitation, hourlyWeather);
			return true;
		} else {
			return false;
		}
	}

	private boolean processLocation(LocationDetails location, List<HourlyPrecipitation> hPrecipitation,
			List<HourlyWeather> hWeather) throws DocumentRequestException {
		Optional<Document> oDoc;

		oDoc = location.requestData(drs);

		if (oDoc.isPresent()) {

			processDocument(oDoc.get(), hPrecipitation, hWeather, location);
			return true;
		} else {
			return false;
		}
	}

	private void processDocument(Document doc, List<HourlyPrecipitation> hPrecipitation, List<HourlyWeather> hWeather,
			LocationDetails location) {

		doc.getDocumentElement().normalize();

		NodeList nodeList = doc.getElementsByTagName("time");

		int n = nodeList.getLength();

		for (int i = 0; i < n; i++) {
			Element element = (Element) nodeList.item(i);
			String from = element.getAttribute("from");

			Date ft;
			try {
				ft = dateFormat.parse(from);

				ItemKey ik = new ItemKey(ft, location);

				Element loc = (Element) element.getElementsByTagName("location").item(0);
				NodeList cn = loc.getElementsByTagName("precipitation");

				int m = cn.getLength();

				if (m == 1) {

					addPrecipitation(cn, ik, hPrecipitation);

				} else {

					addWeather(loc.getChildNodes(), ik, hWeather);

				}

			} catch (ParseException e) {
				logger.log(Level.SEVERE, e.getMessage());

			}

		}

	}

	private void addPrecipitation(NodeList cn, ItemKey ik, List<HourlyPrecipitation> hPrecipitation) {
		Element precip = (Element) cn.item(0);
		String value = precip.getAttribute(VALUE);
		String mnval = precip.getAttribute("minvalue");
		Double mnV = null;
		if (!mnval.equals(""))
			mnV = Double.parseDouble(mnval);
		String mxval = precip.getAttribute("maxvalue");
		Double mxV = null;
		if (!mxval.equals(""))
			mxV = Double.parseDouble(mxval);

		hPrecipitation.add(new HourlyPrecipitation(ik, new PrecipitationValue(Double.parseDouble(value), mnV, mxV)));
	}

	private void addWeather(NodeList locChild, ItemKey ik, List<HourlyWeather> hWeather) {
		int m = locChild.getLength();

		Double t = null;
		WindDirection wd = null;
		WindSpeed ws = null;
		Double h = null;
		Double p = null;
		Double c = null;
		Double lc = null;
		Double mc = null;
		Double hc = null;
		Double dp = null;
		Double f = null;

		for (int j = 0; j < m; j++) {
			Node chNode = locChild.item(j);
			String nn = chNode.getNodeName();

			NamedNodeMap nnm = chNode.getAttributes();
			if (nn.equals("temperature")) {

				String value = textContent(nnm, VALUE);
				t = Double.parseDouble(value);

			} else if (nn.equals("windDirection")) {
				String deg = textContent(nnm, "deg");
				String name = textContent(nnm, "name");
				wd = new WindDirection(Double.parseDouble(deg), name);
			} else if (nn.equals("windSpeed")) {
				String mps = textContent(nnm, "mps");
				String beaufort = textContent(nnm, "beaufort");
				String name = textContent(nnm, "name");
				ws = new WindSpeed(Double.parseDouble(mps), Integer.parseInt(beaufort), name);

			} else if (nn.equals("humidity")) {
				String value = textContent(nnm, VALUE);
				h = Double.parseDouble(value);

			} else if (nn.equals("pressure")) {
				String value = textContent(nnm, VALUE);
				p = Double.parseDouble(value);
			} else if (nn.equals("cloudiness")) {
				String percent = textContent(nnm, PERCENT);
				c = Double.parseDouble(percent);
			} else if (nn.equals("lowClouds")) {
				String percent = textContent(nnm, PERCENT);
				lc = Double.parseDouble(percent);
			} else if (nn.equals("mediumClouds")) {
				String percent = textContent(nnm, PERCENT);
				mc = Double.parseDouble(percent);
			} else if (nn.equals("highClouds")) {
				String percent = textContent(nnm, PERCENT);
				hc = Double.parseDouble(percent);
			} else if (nn.equals("dewpointTemperature")) {
				String value = textContent(nnm, VALUE);
				dp = Double.parseDouble(value);

			} else if (nn.equals("fog")) {
				String percent = textContent(nnm, PERCENT);
				f = Double.parseDouble(percent);

			} else if (!nn.equals("#text")) {

				logger.log(Level.WARNING, () -> "Unknown tag in data converter. Tag name: " + nn);
			}

		}

		Cloud cloud = new Cloud(lc, mc, hc);

		WeatherValue weatherValue = new WeatherValue(t, wd, ws, h, p, c, cloud, dp, f);
		hWeather.add(new HourlyWeather(ik, weatherValue));

	}

	private String textContent(NamedNodeMap nnm, String attName) {
		return nnm.getNamedItem(attName).getTextContent();
	}

	private void store(List<HourlyPrecipitation> hPrecipitation, List<HourlyWeather> hWeather) {

		precipitationRepository.saveAll(hPrecipitation);
		weatherRepository.saveAll(hWeather);
	}

}
