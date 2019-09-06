package eu.acclimatize.unison.location.postgis;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostGISCoordinatesRepository extends JpaRepository<PostGISCoordinates, String>{

}
