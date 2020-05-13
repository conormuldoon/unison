package eu.acclimatize.unison.location;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 
 * A repository for storing location data.
 *
 */
public interface LocationRepository extends JpaRepository<Location, String> {

	/**
	 * Finds the name of the user that added the location.
	 * 
	 * @param locationName The name of the location.
	 * @return The user name that was used in adding the location.
	 */
	@Query("select l.user.userName from Location l where l.name=:locationName")
	public Optional<String> findOwner(String locationName);
}
