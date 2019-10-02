package eu.acclimatize.unison.location.postgis;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 
 * A repository for storing coordinates data in PostGIS.
 *
 */
public interface PostGISCoordinatesRepository extends JpaRepository<PostGISCoordinates, String>{

}
