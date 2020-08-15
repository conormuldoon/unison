package eu.acclimatize.unison.location;

/**
 * An exception that is thrown if there is no features, geometry, properties,
 * coordinates, or location name when deserializing GeoJSON.
 *
 */
public class DeserializationException extends RuntimeException {

	private static final long serialVersionUID = 9035851197861216401L;

	/**
	 * Creates an instance of DeserializationException.
	 * 
	 * @param message The exception message to be displayed or logged.
	 */
	public DeserializationException(String message) {
		super(message);
	}

}
