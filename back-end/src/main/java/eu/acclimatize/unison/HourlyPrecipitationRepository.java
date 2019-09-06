package eu.acclimatize.unison;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HourlyPrecipitationRepository extends CrudRepository<HourlyPrecipitation, ItemKey> { 

	
	@Modifying
	@Query("delete from HourlyPrecipitation hp where hp.key.location.name=:name")
	public void deleteForLocation(String name);
}
