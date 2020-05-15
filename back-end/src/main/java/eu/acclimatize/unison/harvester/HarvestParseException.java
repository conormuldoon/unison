package eu.acclimatize.unison.harvester;

/**
 * An exception class that is thrown when the XML for the location
 * is not found.
 * 
 */
public class HarvestParseException extends Exception{
	

	private static final long serialVersionUID = -7544527923123881454L;

	/**
	 * Creates and instance of LocationRequestException.
	 * 
	 * @param message The detail message.
	 */
	public HarvestParseException(String message){
		super(message);
	}


}
