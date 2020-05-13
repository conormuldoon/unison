package eu.acclimatize.unison.location;

public class LocationExistsException extends RuntimeException {

	private static final long serialVersionUID = 4795650147575045650L;

	public LocationExistsException(String message) {
		super(message);
	}
	
}
