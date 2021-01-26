package eu.acclimatize.unison;

/**
 * An exception thrown if the method parameter from an injection point is null.
 *
 */
public class NoMethodParameterException extends Exception {

	private static final long serialVersionUID = -8329727303728901811L;

	/**
	 * Creates an instance of NoMethodParameterException.
	 * 
	 * @param message The detail message.
	 */
	public NoMethodParameterException(String message) {
		super(message);
	}

}
