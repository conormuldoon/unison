package eu.acclimatize.unison;

/**
 * A class that defines the get mapping values for the CSV, JSON, location, and
 * user controllers.
 *
 */
public class MappingConstant {

	/**
	 * A private constructor to prevent constant from being instantiated by other
	 * classes.
	 */
	private MappingConstant() {

	}

	/**
	 * The mapping for the set of locations.
	 */
	public static final String LOCATION_COLLECTION = "/" + Constant.LOCATION_COLLECTION;

	/**
	 * The mapping for a specific location.
	 */
	public static final String SPECIFIC_LOCATION = LOCATION_COLLECTION + "/{" + Constant.LOCATION_NAME + "}";

	private static final String PREFIX = SPECIFIC_LOCATION + "/";

	/**
	 * The mapping for cloudiness.
	 */
	public static final String LOCATION_CLOUDINESS = PREFIX + Constant.CLOUDINESS;

	/**
	 * The mapping for cloud level.
	 */
	public static final String LOCATION_CLOUD_LEVEL = PREFIX + Constant.CLOUD_LEVEL;

	/**
	 * The mapping for dew point.
	 */
	public static final String LOCATION_DEW_POINT = PREFIX + Constant.DEW_POINT;

	/**
	 * The mapping for fog.
	 */
	public static final String LOCATION_FOG = PREFIX + Constant.FOG;

	/**
	 * The mapping for humidity.
	 */
	public static final String LOCATION_HUMIDITY = PREFIX + Constant.HUMIDITY;

	/**
	 * The mapping for precipitation.
	 */
	public static final String LOCATION_PRECIPITATION = PREFIX + Constant.PRECIPITATION;

	/**
	 * The mapping for pressure.
	 */
	public static final String LOCATION_PRESSURE = PREFIX + Constant.PRESSURE;

	/**
	 * The mapping for temperature.
	 */
	public static final String LOCATION_TEMPERATURE = PREFIX + Constant.TEMPERATURE;

	/**
	 * The mapping for wind direction.
	 */
	public static final String LOCATION_WIND_DIRECTION = PREFIX + Constant.WIND_DIRECTION;

	/**
	 * The mapping for wind speed.
	 */
	public static final String LOCATION_WIND_SPEED = PREFIX + Constant.WIND_SPEED;

	/**
	 * The mapping for adding a user.
	 */
	public static final String USER = "/" + Constant.USER;

	/**
	 * The mapping for the index.
	 */
	public static final String INDEX = "/" + Constant.INDEX;

	/**
	 * The mapping for checking whether a location collection contains a location.
	 */
	public static final String CONTAINS = LOCATION_COLLECTION + "/" + Constant.CONTAINS;

	/**
	 * The mapping for the API explored.
	 **/
	public static final String EXPLORER = "/" + Constant.EXPLORER;

}
