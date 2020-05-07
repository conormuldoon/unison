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
	 * Returned when a controller fails to be perform a task.
	 */
	public static final int FAILURE = 0;

	/**
	 * Returned when a task is successfully executed.
	 */
	public static final int SUCCESS = 1;

	/**
	 * Return when a location has been added, but data has not been received for the
	 * location from a a HARMONIE-AROME API.
	 */
	public static final int DATA_NOT_RECEIVED = 2;
}
