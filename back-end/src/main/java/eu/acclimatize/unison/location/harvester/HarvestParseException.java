package eu.acclimatize.unison.location.harvester;

/**
 * An exception that is thrown if there was a SAX exception when parsing .
 * 
 */
public class HarvestParseException extends Exception {

	private static final long serialVersionUID = -7544527923123881454L;

	/**
	 * Creates and instance of HarvestParseException.
	 * 
	 * @param message The detail message.
	 */
	public HarvestParseException(String message) {
		super(message);
	}

}
