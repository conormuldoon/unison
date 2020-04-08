package eu.acclimatize.unison.location;

import java.util.List;

/**
 * 
 * An interface that defines the contract between generic location classes and spatial database specific classes.
 *
 */
public interface CoordinatesStore {

	/** Saves the coordinates and location details.
	 * 
	 * @param longitude The longitude coordinate.
	 * @param latitude The latitude coordinate.
	 * @param location Generic information, such as the location name, related to the coordinates.
	 */
	public void save(double longitude, double latitude, LocationDetails location);

	/**
	 * Finds a sorted list of coordinates. The sorting order is determines by the {@link CoordinatesConfig#sort} bean.
	 * 
	 * @return An ordered list of coordinates. 
	 */
	public List<? extends CoordinatesSerializer> sortedFindAll();
	
	/**
	 * Removes coordinates from the spatial database.
	 * 
	 * @param name The location name of the coordinates to remove.
	 */
	public void delete(String name);
	
	

}
