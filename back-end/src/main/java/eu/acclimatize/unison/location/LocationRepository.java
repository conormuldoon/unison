package eu.acclimatize.unison.location;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * A repository for storing location data.
 *
 */
public interface LocationRepository extends JpaRepository<Location, String> {
	
}
