package eu.acclimatize.unison.location.harvester;

/**
 * An exception that is thrown if there was an I/O exception when connecting to the URI.
 *
 */
public class HarvestRequestException extends Exception {

	private static final long serialVersionUID = 3104322802205888928L;

	/**
	 * Creates an instance of HarvestRequestException.
	 * 
	 * @param message The message to be displayed or logged.
	 */
	public HarvestRequestException(String message) {
		super(message);
	}

}
