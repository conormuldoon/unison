package eu.acclimatize.unison.location;

/**
 * 
 * An exception that is thrown when there is a problem parsing Well Known Text
 * (WKT) for points.
 *
 */
public class PointParseException extends RuntimeException {

	private static final long serialVersionUID = 5521249264073510924L;

	/**
	 * Creates an instance of PointParseException.
	 * 
	 * @param pointWKT The WKT for the point.
	 * @param cause    A ParseException from the com.vividsolutions or
	 *                 org.locationtech packages.
	 */
	public PointParseException(String pointWKT, Exception cause) {
		super("Problem parsing Well Known Text (WKT) for point: " + pointWKT, cause);
	}

}
