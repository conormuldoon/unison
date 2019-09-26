package eu.acclimatize.unison.location;

/**
 * 
 * A generic wrapper exception class for exceptions thrown when there is a problem parsing the coordinates for a spatial database.
 *
 */
public class CoordinatesParseException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8401382545441534865L;

	/**
	 * Creates an instance of CoordinatesParseException.
	 * 
	 * @param exception The underlying parse exception.
	 */
	public CoordinatesParseException(Exception exception) {
		super(exception);
	}
}
