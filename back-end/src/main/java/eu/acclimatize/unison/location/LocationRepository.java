package eu.acclimatize.unison.location;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<LocationDetails, String> {
	
	@Query("select distinct l from HourlyPrecipitation p join p.key.location l join HourlyWeather w on w.key.fromHour = p.key.fromHour where (select max(w.key.fromHour) from w) < CURRENT_TIMESTAMP")
	public Iterable<LocationDetails> dataRequestRequired();
	
	@Query("from LocationDetails l where (select count(*) from HourlyPrecipitation p where l.name=p.key.location.name) = 0 or (select count(*) from HourlyWeather w where l.name=w.key.location.name) = 0")
	public Iterable<LocationDetails> empty();
	
	@Query("select ld.user.userName from LocationDetails ld where ld.name=:locationName")
	public String findOwner(String locationName);
}
