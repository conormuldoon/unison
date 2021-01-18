package eu.acclimatize.unison;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import eu.acclimatize.unison.location.Location;

/**
 * 
 * A repository for storing hourly weather data.
 *
 */
//@RepositoryRestResource(exported=false)
public interface HourlyWeatherRepository extends CrudRepository<HourlyWeather, ItemKey> {

	/**
	 * Deletes the hourly weather data for a given location.
	 * 
	 * @param location The location to delete data for.
	 */
	@Modifying
	@Query("delete from HourlyWeather hw where hw.key.location=:location")
	void deleteForLocation(Location location);
}
