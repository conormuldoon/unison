package eu.acclimatize.unison.location;


/**
 * 
 * A class that defines response constants for adding and removing locations.
 *
 */
public class ResponseConstant {
	
	// A private constructor to prevent instantiation.
	private ResponseConstant() {
		
	}
	
	/**
	 * Returned when failed to add or remove a location.
	 */
	public static final int FAIL = 0;
	
	/**
	 * Returned when successfully added or removed a location.
	 */
	public static final int SUCCESS = 1;
	
	/**
	 * Returned when the user has given incorrect credentials.
	 */
	public static final int INCORRECT_CREDENTIALS = 2;
	
	/**
	 * Return when a location has been added, but data has not been received for the location from a a HARMONIE-AROME API.
	 */
	public static final int DATA_NOT_RECIEVED = 3;
	
	/**
	 * Returned when a user tries to delete a location that they did not add.
	 */
	public static final int NOT_OWNER = 3;
}
