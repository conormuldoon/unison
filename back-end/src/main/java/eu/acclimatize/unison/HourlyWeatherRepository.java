package eu.acclimatize.unison;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HourlyWeatherRepository extends CrudRepository<HourlyWeather, ItemKey> {

	
	@Modifying
	@Query("delete from HourlyWeather hw where hw.key.location.name=:name")
	public void deleteForLocation(String name);
}
