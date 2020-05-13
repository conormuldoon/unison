package eu.acclimatize.unison.location;

public class LocationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8028511306515065471L;

	public LocationNotFoundException(String locationName) {
		super(locationName+ " was not found.");
	}
}
