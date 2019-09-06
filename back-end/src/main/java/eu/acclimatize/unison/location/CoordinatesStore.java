package eu.acclimatize.unison.location;

public interface CoordinatesStore {

	public void save(String name, double longitude, double latitude,LocationDetails location);

	public Iterable<? extends Object> sortedFindAll();
	
	public void delete(String name);
	
	

}
