package eu.acclimatize.unison;

/**
 * 
 * A class that defines constants.
 *
 */
public class Constant {

	/**
	 * A private constructor to prevent constant from being instantiated by other
	 * classes.
	 */
	private Constant() {

	}

	/**
	 * The link property name for cloudiness.
	 */
	public static final String CLOUDINESS = "cloudiness";

	/**
	 * The link property name for cloud level.
	 */
	public static final String CLOUD_LEVEL = "cloudLevel";

	/**
	 * The link property name for dew point.
	 */
	public static final String DEW_POINT = "dewPoint";

	/**
	 * The link property name for fog.
	 */
	public static final String FOG = "fog";

	/**
	 * The link property name for humidity.
	 */
	public static final String HUMIDITY = "humidity";

	/**
	 * The link property name for precipitation.
	 */
	public static final String PRECIPITATION = "precipitation";

	/**
	 * The link property name for pressure.
	 */
	public static final String PRESSURE = "pressure";

	/**
	 * The link property name for temperature.
	 */
	public static final String TEMPERATURE = "temperature";

	/**
	 * The link property name for wind direction.
	 */
	public static final String WIND_DIRECTION = "windDirection";

	/**
	 * The link property name for wind speed.
	 */
	public static final String WIND_SPEED = "windSpeed";

	/**
	 * The path variable for the user name.
	 */
	public static final String USER_NAME = "userName";

	public static final String USER = "user";

	public static final String INDEX = "index";

	/**
	 * 
	 */
	public static final String LOCATION_COLLECTION = "locationCollection";

	/**
	 * The query string parameter field name, GeoJSON property name, and the JPQL
	 * query parameter name for the location name.
	 */
	public static final String LOCATION_NAME = "name";

	/**
	 * The format for the from date and to date query string parameters.
	 */
	public static final String FORMAT = "d/M/yyyy";

	/**
	 * The query string parameter field name and the JPQL query parameter name for
	 * the from date.
	 */
	public static final String FROM_DATE = "fromDate";

	/**
	 * The query string parameter field name and the JPQL query parameter name for
	 * the to date.
	 */
	public static final String TO_DATE = "toDate";

	/**
	 * The location of the application properties file.
	 */
	public static final String PROPERTIES_FILE = "/application.properties";

	/**
	 * The date/time CSV header text.
	 */
	public static final String FROM_HOUR = "From hour";

	/**
	 * The percentage symbol used in CSV headers.
	 */
	public static final String PERCENTAGE_SYMBOL = "(%)";

	/**
	 * The millimetres symbol used in CSV headers.
	 */
	public static final String MILLIMETRES_SYMBOL = "(mm)";

	/**
	 * The Celsius symbol used in CSV headers.
	 */
	public static final String CELSIUS_SYMBOL = "(°C)";

	/**
	 * The default used by
	 * {@link eu.acclimatize.unison.csvcontroller.CSVHeaderItem}.
	 */
	public static final String CSV_HEADER_DEFAULT = "";

	/**
	 * The GeoJSON type field name.
	 */
	public static final String TYPE = "type";

	/**
	 * The date format used by a HARMONIE-AROME API.
	 */
	public static final String HARMONIE_DATE_FORMAT = "yyyy-MM-dd'T'KK:mm:ss'Z'";

	/**
	 * The roll name for an authenticated user.
	 */
	public static final String ROLL_USER = "ROLE_USER";

	private static final String HAS_OWNER_AUTH = ".hasOwner(authentication.name)";

	/**
	 * The predicate that the item's owner is the authenticated user.
	 */
	public static final String OWNED_ITEM = "#ownedItem" + HAS_OWNER_AUTH;

	/**
	 * The predicated that the current item's owner is the authenticated user and
	 * updated item's owner is the authenticated user.
	 */
	public static final String REPLACE_ITEM = "#current" + HAS_OWNER_AUTH + " and #updated" + HAS_OWNER_AUTH;

}
