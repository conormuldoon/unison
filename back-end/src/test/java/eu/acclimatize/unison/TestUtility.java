package eu.acclimatize.unison;

import java.io.StringWriter;
import java.util.Iterator;

import org.junit.Assert;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.user.UserInformation;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * A utility for tests.
 *
 */
public class TestUtility {

	private static final UserInformation USER_INFORMATION;

	private static final Location LOCATION;

	private static final ItemKey ITEM_KEY;

	private static final WeatherValue WEATHER_VALUE;
	private static final PrecipitationValue PRECIPITATION_VALUE;

	static {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(TestConstant.PASSWORD);
		USER_INFORMATION = new UserInformation(TestConstant.USERNAME, encodedPassword);

		LOCATION = createLocation(TestConstant.LOCATION, USER_INFORMATION, TestConstant.LONGITUDE,
				TestConstant.LATITUDE);

		ITEM_KEY = new ItemKey(TestConstant.FROM_DATE, LOCATION);

		WindDirection wd = new WindDirection(0, "Lett bris");
		WindSpeed ws = new WindSpeed(0, 0, "W");
		Cloud cloud = new Cloud(0d, 0d, 0d);
		WEATHER_VALUE = new WeatherValue(0d, wd, ws, 0d, 0d, 0d, cloud, 0d, 0d);

		PRECIPITATION_VALUE = new PrecipitationValue(0d, 0d, 0d);
	}

	/**
	 * A private constructor to prevent test utility from being instantiated by
	 * other classes.
	 **/
	private TestUtility() {

	}

	/**
	 * Saves user information and location data in the database.
	 * 
	 * @param userRepository     The repository user information is saved in.
	 * @param locationRepository The repository location data is saved in.
	 */
	public static void saveLocationData(UserRepository userRepository, LocationRepository locationRepository) {

		userRepository.save(USER_INFORMATION);
		locationRepository.save(LOCATION);

	}

	/**
	 * Deletes user information and location data from the database.
	 * 
	 * @param locationRepository The repository location data is deleted from.
	 * @param userRepository     The repository user information is deleted from.
	 */
	public static void deleteLocationData(LocationRepository locationRepository, UserRepository userRepository) {
		locationRepository.deleteAll();
		userRepository.deleteAll();
	}

	/**
	 * Saves weather data, and associated user and location, in the database.
	 * 
	 * @param userRepository     The repository user information is saved in.
	 * @param locationRepository The repository location data is saved in.
	 * @param hwr                The repository weather data is saved in.
	 */
	public static void saveWeatherData(UserRepository userRepository, LocationRepository locationRepository,
			HourlyWeatherRepository hwr) {

		saveLocationData(userRepository, locationRepository);

		HourlyWeather weatherData = new HourlyWeather(ITEM_KEY, WEATHER_VALUE);

		hwr.save(weatherData);
	}

	/**
	 * Deletes weather data, and associated user and location, from the database.
	 * 
	 * @param hwr                The repository weather data is deleted from.
	 * @param userRepository     The repository user information is deleted from.
	 * @param locationRepository The repository location data is deleted from.
	 */
	public static void deleteWeatherData(HourlyWeatherRepository hwr, LocationRepository locationRepository,
			UserRepository userRepository) {
		hwr.deleteAll();
		deleteLocationData(locationRepository, userRepository);

	}

	/**
	 * Saves the given precipitation data, and associated user and location, in the
	 * database.
	 * 
	 * @param userRepository     The repository user information is saved in.
	 * @param locationRepository The repository location data is saved in.
	 * @param precipitationValue The precipitation value saved.
	 * @param hpr                The repository precipitation data is saved in.
	 */
	public static void savePrecipitationData(UserRepository userRepository, LocationRepository locationRepository,
			PrecipitationValue precipitationValue, HourlyPrecipitationRepository hpr) {
		HourlyPrecipitation precip = new HourlyPrecipitation(ITEM_KEY, precipitationValue);

		saveLocationData(userRepository, locationRepository);
		hpr.save(precip);
	}

	/**
	 * Saves precipitation, and associated user and location, data in the database.
	 * 
	 * @param userRepository     The repository user information is saved in.
	 * @param locationRepository The repository location data is saved in.
	 * @param hpr                The repository precipitation data is saved in.
	 */
	public static void savePrecipitationData(UserRepository userRepository, LocationRepository locationRepository,
			HourlyPrecipitationRepository hpr) {

		savePrecipitationData(userRepository, locationRepository, PRECIPITATION_VALUE, hpr);
	}

	/**
	 * Deletes precipitation, and associated user and location, data form the
	 * database.
	 * 
	 * @param hpr                The repository the precipitation data is deleted
	 *                           from.
	 * @param userRepository     The repository user information is deleted from.
	 * @param locationRepository The repository location data is deleted from.
	 */
	public static void deletePrecipitationData(HourlyPrecipitationRepository hpr, LocationRepository locationRepository,
			UserRepository userRepository) {
		hpr.deleteAll();
		deleteLocationData(locationRepository, userRepository);
	}

	/**
	 * Creates a Location object.
	 * 
	 * @param locationName    The name of the location.
	 * @param userInformation The user information associated with the location.
	 * @param longitude       The location's longitude coordinate.
	 * @param latitude        The locations's latitude coordinate.
	 * @return The location created.
	 */
	public static final Location createLocation(String locationName, UserInformation userInformation, double longitude,
			double latitude) {
		Point point = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
		Location location = new Location(locationName, userInformation, point);
		return location;

	}

	/**
	 * Asserts that the first element is of the specified type.
	 * 
	 * @param list A collection of items.
	 * @param cls  The required type.
	 */
	public static void assertType(Iterable<HarmonieItem> list, Class<? extends HarmonieItem> cls) {

		Iterator<HarmonieItem> itr = list.iterator();
		HarmonieItem item = itr.next();
		Assert.assertTrue(cls.isInstance(item));
	}

	/**
	 * Asserts that the correct number of CSV columns have be written.
	 * 
	 * @param sw  The writer the CSV is written to.
	 * @param len The required number.
	 */
	public static void assertLength(StringWriter sw, int len) {
		String[] str = sw.toString().split("\n");
		String[] st0 = str[0].split(",");
		String[] st1 = str[1].split(",");
		Assert.assertEquals(len, st1.length);
		Assert.assertEquals(len, st0.length);
	}

}
