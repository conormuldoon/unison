package eu.acclimatize.unison.location;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * An interface for a repository that stores {@link LocationDetails} information.
 *
 */
public interface LocationRepository extends CrudRepository<LocationDetails, String> {
	
	
	
	/**
	 * Queries the database to determine the name of the user that added the location.
	 * 
	 * @param locationName The name of the location.
	 * @return The name of the user that added the location.
	 */
	@Query("select ld.user.userName from LocationDetails ld where ld.name=:locationName")
	public String findOwner(String locationName);
}
