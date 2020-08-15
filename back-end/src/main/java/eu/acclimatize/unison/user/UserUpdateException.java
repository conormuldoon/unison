package eu.acclimatize.unison.user;

/**
 * An exception that is thrown if there is a problem updating user information.
 *
 */
public class UserUpdateException extends RuntimeException{


	private static final long serialVersionUID = 7486124326113362327L;

	/**
	 * Creates and instance of UserUpdateException.
	 * 
	 * @param message The message to be displayed or logged.
	 */
	public UserUpdateException(String message) {
		super(message);
	}
}
