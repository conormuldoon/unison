package eu.acclimatize.unison.location;

/**
 * An exception thrown if the location with the specified name was not found.
 *
 */
public class LocationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8028511306515065471L;

	/**
	 * Creates an instance of LocationNotFoundException.
	 * 
	 * @param locationName The name of the location.
	 */
	public LocationNotFoundException(String locationName) {
		super(locationName + " was not found.");
	}
}
