package eu.acclimatize.unison;

import java.io.StringWriter;
import java.util.Iterator;

import org.junit.Assert;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.security.crypto.password.PasswordEncoder;

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

	private static final PasswordEncoder PASSWORD_ENCODER;

	static {

		UnisonSecurityConfig unisonSecurityConfig = new UnisonSecurityConfig(null);
		PASSWORD_ENCODER = unisonSecurityConfig.passwordEncoder();

		USER_INFORMATION = createUserInformation(TestConstant.USERNAME, TestConstant.PASSWORD);

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
	 * Creates an {@link eu.acclimatize.unison.user.UserInformation} object for the
	 * specified user name and password. The password is encoded prior to the object
	 * being created.
	 * 
	 * @param userName The user name of the user.
	 * @param password The password of the user.
	 * @return The created user information object.
	 */
	public static UserInformation createUserInformation(String userName, String password) {

		String encodedPassword = PASSWORD_ENCODER.encode(password);
		UserInformation userInfo = new UserInformation(userName, encodedPassword);
		return userInfo;
	}

	/**
	 * Creates an {@link eu.acclimatize.unison.user.UserInformation} object for the
	 * specified user name and password and saves the user information in the
	 * repository. The password is encoded prior to the object being created.
	 * 
	 * @param userName       The user name of the user.
	 * @param password       The password of the user.
	 * @param userRepository The repository where the user informatin is stored.
	 */
	public static void addUserInformation(String userName, String password, UserRepository userRepository) {
		UserInformation userInformation = createUserInformation(userName, password);
		userRepository.save(userInformation);
	}

	/**
	 * A private constructor to prevent test utility from being instantiated by
	 * other classes.
	 **/
	private TestUtility() {

	}

	/**
	 * Saves user information in the database.
	 * 
	 * @param userRepository The repository user information is saved in.
	 */
	public static void saveUserData(UserRepository userRepository) {
		userRepository.save(USER_INFORMATION);
	}

	/**
	 * Deletes user information from the database.
	 * 
	 * @param userRepository The repository user information is deleted from.
	 */
	public static void deleteUserData(UserRepository userRepository) {
		userRepository.deleteAll();
	}

	/**
	 * Saves user information and location data in the database.
	 * 
	 * @param userRepository     The repository user information is saved in.
	 * @param locationRepository The repository location data is saved in.
	 */
	public static void saveLocationData(UserRepository userRepository, LocationRepository locationRepository) {

		saveUserData(userRepository);
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
		deleteUserData(userRepository);
	}

	/**
	 * Saves weather, and associated user and location, data in the database.
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
	 * Deletes weather, and associated user and location, data from the database.
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
	 * Saves the given precipitation, and associated user and location, data in the
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
	public static void assertType(Iterable<? extends HarmonieItem> list, Class<? extends HarmonieItem> cls) {

		Iterator<? extends HarmonieItem> itr = list.iterator();
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

	/**
	 * Encrypts a password.
	 * 
	 * @param password The password to encrypt.
	 * @return The encoded password.
	 */
	public static String encode(String password) {
		return PASSWORD_ENCODER.encode(password);
	}

}
