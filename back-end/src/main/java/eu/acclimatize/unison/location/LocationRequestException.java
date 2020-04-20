package eu.acclimatize.unison.location;

/**
 * An exception class that is thrown when the XML for the location
 * is not found.
 * 
 */
public class LocationRequestException extends Exception{
	

	private static final long serialVersionUID = -7544527923123881454L;

	/**
	 * Creates and instance of LocationRequestException.
	 * 
	 * @param message The detail message.
	 */
	public LocationRequestException(String message){
		super(message);
	}

}
