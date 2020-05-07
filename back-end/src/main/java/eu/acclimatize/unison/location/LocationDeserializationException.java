package eu.acclimatize.unison.location;

/**
 * An exception that is thrown if there is no geometry, properties, coordinates,
 * or location name field in a GeoJSON point feature when deserializing .
 *
 */
public class LocationDeserializationException extends RuntimeException {

	private static final long serialVersionUID = 9035851197861216401L;

	public LocationDeserializationException(String message) {
		super(message);
	}

}
