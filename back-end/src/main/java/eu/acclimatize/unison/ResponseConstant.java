package eu.acclimatize.unison;

/**
 * 
 * A class that defines response constants for adding and removing locations and
 * adding/updating user credentials.
 *
 */
public class ResponseConstant {

	// A private constructor to prevent instantiation.
	private ResponseConstant() {

	}

	/**
	 * Returned when a controller fails to be perform a task or the task is not
	 * successful.
	 */
	public static final int FAIL = 0;

	/**
	 * Returned when a task is successfully executed.
	 */
	public static final int SUCCESS = 1;

	/**
	 * Returned when the user has given incorrect credentials.
	 */
	public static final int INCORRECT_CREDENTIALS = 2;

	/**
	 * Return when a location has been added, but data has not been received for the
	 * location from a a HARMONIE-AROME API.
	 */
	public static final int DATA_NOT_RECIEVED = 3;

	/**
	 * Returned when a user tries to delete a location that they did not add.
	 */
	public static final int NOT_OWNER = 3;
}
