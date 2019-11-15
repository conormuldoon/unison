package eu.acclimatize.unison;

/**
 * 
 * A class that defines constants.
 *
 */
public class Constant {

	// A private constructor to prevent constant from being instantiated by other
	// classes.
	private Constant() {

	}

	/**
	 * The field name for the location query string parameter.
	 */
	public static final String LOCATION = "location";

	/**
	 * The format for the from date and to date query string parameters.
	 */
	public static final String FORMAT = "d/M/yyyy";

	/**
	 * The field name for the from date query string parameter.
	 */
	public static final String FROM_DATE = "fromDate";

	/**
	 * The field name for the to date query string parameter.
	 */
	public static final String TO_DATE = "toDate";

	/**
	 * The field name for the user name query string parameter.
	 */
	public static final String USERNAME = "username";

	/**
	 * The field name for the password query string parameter.
	 */
	public static final String PASSWORD = "password";

	/**
	 * The location of the application properties file.
	 */
	public static final String PROPERTIES_FILE = "/application.properties";

	/**
	 * The field name for the new user name query string parameter.
	 */
	public static final String NEW_USERNAME = "newUsername";

	/**
	 * The field name for the new password query string parameter.
	 */
	public static final String NEW_PASSWORD = "newPassword";

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

}
