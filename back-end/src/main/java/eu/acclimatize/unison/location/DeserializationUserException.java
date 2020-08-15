package eu.acclimatize.unison.location;

/**
 * An exception that is thrown is if there is a problem in deserializing 
 * user information.
 *
 */
public class DeserializationUserException extends RuntimeException{

	private static final long serialVersionUID = 6230005248155962444L;

	/**
	 * Creates an instance of DeserializationUserException.
	 * @param message The exception message that will be displayed or logged.
	 */
	public DeserializationUserException(String message) {
		super(message);
	}
}
