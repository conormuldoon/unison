package eu.acclimatize.unison.location.harvester;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.acclimatize.unison.Cloud;
import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HourlyPrecipitation;
import eu.acclimatize.unison.HourlyWeather;
import eu.acclimatize.unison.ItemKey;
import eu.acclimatize.unison.PrecipitationValue;
import eu.acclimatize.unison.WeatherValue;
import eu.acclimatize.unison.WindDirection;
import eu.acclimatize.unison.WindSpeed;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationRepository;

/**
 * A service that harvest data from a HARMONIE-AROME endpoint.
 */
@Service
public class HarvesterService {

	private static final int SLEEP_TIME = 60000;
	private static final String VALUE = "value";
	private static final String PERCENT = "percent";

	private LocationRepository locationRepository;

	private DocumentRequestService lrs;

	private List<HourlyPrecipitation> precipitation;
	private List<HourlyWeather> weather;

	private Logger logger;

	private DateFormat dateFormat;

	private Executor executor;

	private Set<String> displayedUnknown;

	private List<UnknownWV> unknown;

	private StorageService storageService;

	/**
	 * Creates an instance of HarvesterService.
	 * 
	 * @param locationRepository      The repository that stores the locations that
	 *                                data will be harvested for.
	 * @param precipitationRepository The repository where precipitation data is
	 *                                stored.
	 * @param weatherRepository       The repository where non-precipitation weather
	 *                                data is stored.
	 * @param lrs                     The service that is used to obtain XML weather
	 *                                data documents for locations.
	 * @param logger                  Logs warning messages and exceptions.
	 * @param harmonieDateFormat      Used to parse date data using a given time
	 *                                zone.
	 * @param executor                Used to execute the data harvesting process on
	 *                                a thread.
	 */
	public HarvesterService(LocationRepository locationRepository, DocumentRequestService lrs, Logger logger,
			DateFormat harmonieDateFormat, Executor executor, StorageService storageService) {

		this.locationRepository = locationRepository;

		this.lrs = lrs;

		precipitation = new ArrayList<>();
		weather = new ArrayList<>();
		this.logger = logger;

		this.dateFormat = harmonieDateFormat;
		this.executor = executor;

		displayedUnknown = new HashSet<>();

		unknown = new ArrayList<>();

		this.storageService = storageService;

	}

	/**
	 * Requests and data stores from a HARMONIE-AROME API for locations in the
	 * database.
	 * 
	 */
	public void harvestData() {
		executor.execute(() -> {

			try {
				harvestData(locationRepository.findAll());
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, "Interruped when invoking harvestData.%n{0}", e.getMessage());

				// Restoring interrupted state.
				Thread.currentThread().interrupt();
			}

			storageService.store(precipitation, weather);
			
			storageService.storeUnknown(unknown);
			precipitation.clear();
			weather.clear();
			unknown.clear();

		});

	}

	private void harvestData(Iterable<? extends Location> iterable) throws InterruptedException {

		for (Location location : iterable) {

			boolean fetchException;
			do {
				fetchException = false;
				try {

					fetchWeatherData(location);

				} catch (HarvestRequestException e) {
					logger.log(Level.SEVERE, e.getMessage());
					fetchException = true;
					Thread.sleep(SLEEP_TIME);
				} catch (DocumentNotFoundException | HarvestParseException e) {
					logger.log(Level.WARNING, e.getMessage());
				}

			} while (fetchException);
		}

	}

	/**
	 * Requests and stores weather data from a HARMONIE-AROME API for a given
	 * location.
	 * 
	 * @param ownedItem The location to obtain data for.
	 * 
	 * @throws HarvestParseException     Thrown if there was a SAX exception when
	 *                                   parsing.
	 * @throws HarvestRequestException   Thrown if there was an I/O exception when
	 *                                   connecting to the URI.
	 * @throws DocumentNotFoundException Thrown if there was no XML document found
	 *                                   at the URI.
	 * 
	 */
	@PreAuthorize(Constant.OWNED_ITEM)
	public void fetchAndStore(Location ownedItem)
			throws HarvestParseException, HarvestRequestException, DocumentNotFoundException {

		Document document = lrs.documentForLocation(ownedItem);

		List<HourlyPrecipitation> hourlyPrecipitation = new ArrayList<>();
		List<HourlyWeather> hourlyWeather = new ArrayList<>();
		processDocument(document, hourlyPrecipitation, hourlyWeather, ownedItem);
		storageService.store(hourlyPrecipitation, hourlyWeather);

	}

	private void fetchWeatherData(Location location)
			throws HarvestParseException, HarvestRequestException, DocumentNotFoundException {
		Document document = lrs.documentForLocation(location);

		processDocument(document, precipitation, weather, location);

	}

	private void processDocument(Document doc, List<HourlyPrecipitation> hPrecipitation, List<HourlyWeather> hWeather,
			Location location) {

		doc.getDocumentElement().normalize();

		NodeList nodeList = doc.getElementsByTagName("time");

		int n = nodeList.getLength();

		for (int i = 0; i < n; i++) {
			Element element = (Element) nodeList.item(i);
			String from = element.getAttribute("from");

			Date ft;
			try {
				ft = dateFormat.parse(from);

				Element loc = (Element) element.getElementsByTagName("location").item(0);
				NodeList cn = loc.getElementsByTagName("precipitation");

				int m = cn.getLength();

				if (m == 1) {
					ItemKey ik = new ItemKey(ft, location);
					addPrecipitation(cn, ik, hPrecipitation);

				} else {

					addWeather(loc.getChildNodes(), ft, location, hWeather);

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
		if (!"".equals(mnval)) {
			mnV = Double.parseDouble(mnval);
			if (mnV < 0)
				mnV = null;

		}

		String mxval = precip.getAttribute("maxvalue");
		Double mxV = null;
		if (!"".equals(mxval)) {
			mxV = Double.parseDouble(mxval);
			if (mxV < 0)
				mxV = null;
		}

		hPrecipitation.add(new HourlyPrecipitation(ik, new PrecipitationValue(Double.parseDouble(value), mnV, mxV)));
	}

	private void addWeather(NodeList locChild, Date ft, Location location, List<HourlyWeather> hWeather) {
		ItemKey ik = new ItemKey(ft, location);

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
		Double gr = null;

		for (int j = 0; j < m; j++) {
			Node chNode = locChild.item(j);
			String nn = chNode.getNodeName();

			NamedNodeMap nnm = chNode.getAttributes();

			switch (nn) {
			case Constant.TEMPERATURE:
				String value = textContent(nnm, VALUE);
				t = Double.parseDouble(value);
				break;
			case Constant.WIND_DIRECTION:
				String deg = textContent(nnm, "deg");
				String name = textContent(nnm, "name");
				wd = new WindDirection(Double.parseDouble(deg), name);
				break;
			case Constant.WIND_SPEED:
				String mps = textContent(nnm, "mps");
				String beaufort = textContent(nnm, "beaufort");
				name = textContent(nnm, "name");
				ws = new WindSpeed(Double.parseDouble(mps), Integer.parseInt(beaufort), name);
				break;
			case Constant.HUMIDITY:
				value = textContent(nnm, VALUE);
				h = Double.parseDouble(value);
				break;
			case Constant.PRESSURE:
				value = textContent(nnm, VALUE);
				p = Double.parseDouble(value);
				break;
			case Constant.CLOUDINESS:
				String percent = textContent(nnm, PERCENT);
				c = Double.parseDouble(percent);
				break;
			case "lowClouds":
				percent = textContent(nnm, PERCENT);
				lc = Double.parseDouble(percent);
				break;
			case "mediumClouds":
				percent = textContent(nnm, PERCENT);
				mc = Double.parseDouble(percent);
				break;
			case "highClouds":
				percent = textContent(nnm, PERCENT);
				hc = Double.parseDouble(percent);
				break;
			case "dewpointTemperature":
				value = textContent(nnm, VALUE);
				dp = Double.parseDouble(value);
				break;
			case Constant.FOG:
				percent = textContent(nnm, PERCENT);
				f = Double.parseDouble(percent);
				break;
			case Constant.GLOBAL_RADIATION:
				String globalRadiation = textContent(nnm, VALUE);
				gr = Double.parseDouble(globalRadiation);
				break;
			default:
				if (!"#text".equals(nn)) {
					if (!displayedUnknown.contains(nn)) {
						displayedUnknown.add(nn);
						logger.log(Level.WARNING,
								() -> "Unknown weather variable type in data converter. Tag name: " + nn);
					}

					int n = nnm.getLength();
					Set<String> item = new HashSet<>();
					for (int i = 0; i < n; i++) {
						item.add(nnm.item(i).toString());

					}
					UnknownKey uKey = new UnknownKey(ft, location, nn);
					unknown.add(new UnknownWV(uKey, item));

				}
			}

		}

		Cloud cloud = new Cloud(lc, mc, hc);

		WeatherValue weatherValue = new WeatherValue(t, wd, ws, h, p, c, cloud, dp, f, gr);
		hWeather.add(new HourlyWeather(ik, weatherValue));

	}

	private String textContent(NamedNodeMap nnm, String attName) {
		return nnm.getNamedItem(attName).getTextContent();
	}

}
