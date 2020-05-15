package eu.acclimatize.unison;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import eu.acclimatize.unison.location.Location;

/**
 * A repository for storing hourly precipitation data.
 *
 */
public interface HourlyPrecipitationRepository extends CrudRepository<HourlyPrecipitation, ItemKey> { 

	
	/**
	 * Deletes the hourly precipitation data for a given location.
	 * @param name The name of the location.
	 */
	@Modifying
	@Query("delete from HourlyPrecipitation hp where hp.key.location=:location")
	public void deleteForLocation(Location location);
}
