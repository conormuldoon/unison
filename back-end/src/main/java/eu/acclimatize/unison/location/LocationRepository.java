package eu.acclimatize.unison.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 
 * A repository for storing location data.
 *
 */
public interface LocationRepository extends JpaRepository<Location, String> {
	
	
	
	/**
	 * Queries the database to determine the name of the user that added the location.
	 * 
	 * @param locationName The name of the location.
	 * @return The name of the user that added the location.
	 */
	@Query("select ld.user.userName from Location ld where ld.name=:locationName")
	public String findOwner(String locationName);
}
