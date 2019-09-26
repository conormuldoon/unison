package eu.acclimatize.unison.harvester;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

	final static private int SLEEP_TIME = 60000;

	private static final int NUM_HOUR = 6; // The Met Eireann model updates every 6 hours.

	private LocationRepository locationRepository;

	private HourlyPrecipitationRepository precipitationRepository;
	private HourlyWeatherRepository weatherRepository;
	private DocumentRequestService drs;

	private List<HourlyPrecipitation> precipitation;
	private List<HourlyWeather> weather;

	private Logger logger;

	private SimpleDateFormat dateFormat;

	/**
	 * Creates an instance of HarvesterService.
	 * 
	 * @param locationRepository The repository that stores the locations that data will be harvested for.
	 * @param precipitationRepository The repository where precipitation data is stored.
	 * @param weatherRepository The repository where non-precipitation weather data is stored.
	 * @param drs The service that is used to obtain XML documents from the API.
	 * @param logger Logs warning messages.
	 * @param simpleDateFormat Used to parse date data using a given time zone.
	 */
	public HarvesterService(LocationRepository locationRepository,
			HourlyPrecipitationRepository precipitationRepository, HourlyWeatherRepository weatherRepository,
			DocumentRequestService drs, Logger logger, SimpleDateFormat simpleDateFormat) {
		
		this.locationRepository = locationRepository;
		this.weatherRepository = weatherRepository;
		this.precipitationRepository = precipitationRepository;
		this.drs = drs;

		precipitation = new ArrayList<>();
		weather = new ArrayList<>();
		this.logger = logger;

		this.dateFormat = simpleDateFormat;

	}

	/**
	 * Requests and data stores from a HARMONIE-AROME API for locations in the database. 
	 * 
	 * @param calendar Used to determine the time at which to store data.
	 * @throws InterruptedException The service will sleep for a second if it is having problem obtaining data from the API. 
	 * The exception will be thrown if the service is interrupted during this time.
	 */
	synchronized public void harvestData(Calendar calendar) throws InterruptedException {

		Iterable<Date> modelTime = createModelTime(calendar);

		harvestData(locationRepository.dataRequestRequired(), modelTime);

		harvestData(locationRepository.empty(), modelTime);

		store(precipitation, weather);
		precipitation.clear();
		weather.clear();

	}

	private void harvestData(Iterable<? extends LocationDetails> iterable, Iterable<Date> modelTime)
			throws InterruptedException {
		for (LocationDetails loc : iterable) {

			// If data is not received from the API, sleeps and then attempts to connect
			// again.
			while (!processLocation(loc, precipitation, weather, modelTime)) {
				Thread.sleep(SLEEP_TIME);
			}

		}

	}

	private Date createDate(String s) throws ParseException {

		Date date = dateFormat.parse(s);
		return date;
	}

	private Iterable<Date> createModelTime(Calendar calendar) {

		// Required in case the harvest was either invoked at start time, rather than
		// scheduled, or triggered by a location being added.
		int hr = calendar.get(Calendar.HOUR_OF_DAY);
		int md = hr % NUM_HOUR;
		calendar.add(Calendar.HOUR_OF_DAY, -md);

		// On the hour
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		List<Date> list = new ArrayList<>();
		list.add(calendar.getTime());

		for (int i = 1; i < NUM_HOUR; i++) {
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			list.add(calendar.getTime());
		}
		return list;
	}

	private Map<Date, ItemKey> createKeyMap(Iterable<Date> modelTime, LocationDetails location) {
		Map<Date, ItemKey> map = new HashMap<>();

		for (Date d : modelTime) {

			ItemKey ik = new ItemKey(d, location);
			map.put(d, ik);
		}
		return map;

	}

	/**
	 * Requests and stores data from a HARMONIE-AROME API for a given location.
	 * 
	 * @param location The location to obtain data for.
	 * @param calendar Used to determine the times to store data for.
	 * @return True if the data was received and stored, false otherwise.
	 */
	synchronized public boolean processLocation(LocationDetails location, Calendar calendar) {

		List<HourlyPrecipitation> hourlyPrecipitation = new ArrayList<>();
		List<HourlyWeather> hourlyWeather = new ArrayList<>();
		if (processLocation(location, hourlyPrecipitation, hourlyWeather, createModelTime(calendar))) {
			store(hourlyPrecipitation, hourlyWeather);
			return true;
		} else {
			return false;
		}
	}

	private boolean processLocation(LocationDetails location, List<HourlyPrecipitation> hPrecipitation,
			List<HourlyWeather> hWeather, Iterable<Date> dateIterable) {
		Optional<Document> oDoc = location.requestData(drs);

		if (oDoc.isPresent()) {
			Map<Date, ItemKey> keyMap = createKeyMap(dateIterable, location);
			processDocument(oDoc.get(), keyMap, hPrecipitation, hWeather);
			return true;
		} else {
			return false;
		}
	}

	private void processDocument(Document doc, Map<Date, ItemKey> keyMap, List<HourlyPrecipitation> hPrecipitation,
			List<HourlyWeather> hWeather) {

		doc.getDocumentElement().normalize();

		NodeList nodeList = doc.getElementsByTagName("time");

		int n = nodeList.getLength();

		for (int i = 0; i < n; i++) {
			Element element = (Element) nodeList.item(i);
			String from = element.getAttribute("from");

			Date ft;
			try {
				ft = createDate(from);

				if (keyMap.containsKey(ft)) {

					ItemKey ik = keyMap.get(ft);
					Element loc = (Element) element.getElementsByTagName("location").item(0);
					NodeList cn = loc.getElementsByTagName("precipitation");

					int m = cn.getLength();

					if (m == 1) {

						addPrecipitation(cn, ik, hPrecipitation);

					} else {

						addWeather(loc.getChildNodes(), ik, hWeather);

					}

				}
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

	}

	private void addPrecipitation(NodeList cn, ItemKey ik, List<HourlyPrecipitation> hPrecipitation) {
		Element precip = (Element) cn.item(0);
		String value = precip.getAttribute("value");
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

				String value = textContent(nnm, "value");
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
				String value = textContent(nnm, "value");
				h = Double.parseDouble(value);

			} else if (nn.equals("pressure")) {
				String value = textContent(nnm, "value");
				p = Double.parseDouble(value);
			} else if (nn.equals("cloudiness")) {
				String percent = textContent(nnm, "percent");
				c = Double.parseDouble(percent);
			} else if (nn.equals("lowClouds")) {
				String percent = textContent(nnm, "percent");
				lc = Double.parseDouble(percent);
			} else if (nn.equals("mediumClouds")) {
				String percent = textContent(nnm, "percent");
				mc = Double.parseDouble(percent);
			} else if (nn.equals("highClouds")) {
				String percent = textContent(nnm, "percent");
				hc = Double.parseDouble(percent);
			} else if (nn.equals("dewpointTemperature")) {
				String value = textContent(nnm, "value");
				dp = Double.parseDouble(value);

			} else if (nn.equals("#text")) {
				// System.out.println(chNode.getTextContent());
			} else if (nn.equals("fog")) {
				String percent = textContent(nnm, "percent");
				f = Double.parseDouble(percent);
			} else {
				// throw new UnknownTagException(nn);
				logger.log(Level.WARNING, "Unknown tag in data converter. Tag name: " + nn);
			}

		}

		Cloud cloud = new Cloud(lc, mc, hc);

		WeatherValue weatherValue = new WeatherValue(t, wd, ws, h, p, c, cloud, dp, f);
		hWeather.add(new HourlyWeather(ik, weatherValue));

	}

	private String textContent(NamedNodeMap nnm, String attName) {
		return nnm.getNamedItem(attName).getTextContent();
	}

	@Transactional
	private void store(List<HourlyPrecipitation> hPrecipitation, List<HourlyWeather> hWeather) {

		precipitationRepository.saveAll(hPrecipitation);
		weatherRepository.saveAll(hWeather);
	}

}
