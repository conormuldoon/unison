package eu.acclimatize.unison.user;

public class AnonymousUserException extends RuntimeException{

	private static final long serialVersionUID = 6230005248155962444L;

	public AnonymousUserException(String message) {
		super(message);
	}
}
