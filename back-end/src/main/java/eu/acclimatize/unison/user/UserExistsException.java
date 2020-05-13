package eu.acclimatize.unison.user;

public class UserExistsException extends RuntimeException {

	private static final long serialVersionUID = 7834260569181022098L;

	public UserExistsException(String message) {
		super(message);
	}

}
