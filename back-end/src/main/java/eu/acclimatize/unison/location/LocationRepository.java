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
	 * Determines the locations that are due to have their weather and precipitation data updated from the API. 
	 * 
	 * @return Locations that are due to be updated.
	 */
	@Query("select distinct l from HourlyPrecipitation p join p.key.location l join HourlyWeather w on w.key.fromHour = p.key.fromHour where (select max(w.key.fromHour) from w) < CURRENT_TIMESTAMP")
	public Iterable<LocationDetails> dataRequestRequired();
	
	/**
	 * Determines locations that have not received or stored data from the API.
	 * 
	 * @return Locations that have not data.
	 */
	@Query("from LocationDetails l where (select count(*) from HourlyPrecipitation p where l.name=p.key.location.name) = 0 or (select count(*) from HourlyWeather w where l.name=w.key.location.name) = 0")
	public Iterable<LocationDetails> empty();
	
	/**
	 * Queries the database to determine the name of the user that added the location.
	 * 
	 * @param locationName The name of the location.
	 * @return The name of the user that added the location.
	 */
	@Query("select ld.user.userName from LocationDetails ld where ld.name=:locationName")
	public String findOwner(String locationName);
}
