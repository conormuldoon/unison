package eu.acclimatize.unison.location.geodb;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * A repository for storing coordinates data in GeoDB.
 *
 */
public interface GeoDBCoordinatesRepository extends JpaRepository<GeoDBCoordinates, String>{

}
