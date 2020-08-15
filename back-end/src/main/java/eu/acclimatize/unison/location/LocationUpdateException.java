package eu.acclimatize.unison.location;

/**
 * Thrown is there is an issue in updating the location data. 
 *
 */
public class LocationUpdateException extends RuntimeException{

	private static final long serialVersionUID = -7926273768115644573L;

	/**
	 * Creates an instance of LocationUpdateException.
	 * 
	 * @param message The message to be displayed or logged.
	 */
	public LocationUpdateException(String message) {
		super(message);
	}
}
