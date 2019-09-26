package eu.acclimatize.unison.location.postgis;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import eu.acclimatize.unison.location.CoordinatesStore;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.location.CoordinatesParseException;

@Repository
public class PostGISStore implements CoordinatesStore {

	private PostGISCoordinatesRepository repository;


	private WKTReader wktR;

	private Sort sort;

	public PostGISStore(PostGISCoordinatesRepository repository, LocationRepository locationRepository, Sort sort) {
		this.repository = repository;
		
		wktR = new WKTReader();
		this.sort = sort;
	}


	@Override
	public void save(double longitude, double latitude, LocationDetails location) {
	
		Point p;
		try {
			p = (Point) wktR.read("POINT (" + longitude + " " + latitude + ")");
			PostGISCoordinates coord = new PostGISCoordinates( p, location);
			repository.save(coord);
		} catch (ParseException e) {
			throw new CoordinatesParseException(e);
		}

	}

	@Override
	public void delete(String locationName) {
		repository.deleteById(locationName);
	}

	@Override
	public Iterable<? extends Object> sortedFindAll() {
		return repository.findAll(sort);
	}

}
