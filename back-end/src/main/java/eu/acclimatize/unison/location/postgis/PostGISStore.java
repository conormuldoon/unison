package eu.acclimatize.unison.location.postgis;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import eu.acclimatize.unison.location.CoordinatesParseException;
import eu.acclimatize.unison.location.CoordinatesStore;
import eu.acclimatize.unison.location.LocationDetails;

/**
 * 
 * A wrapper repository for the {@link PostGISCoordinatesRepository} that hides dependencies on the 
 * com.vividsolutions package from classes it is injected to.
 *
 */
@Repository
public class PostGISStore implements CoordinatesStore {

	private PostGISCoordinatesRepository repository;

	private WKTReader wktR;

	private Sort sort;

	/**
	 * Creates an instance of PostGISStore.
	 * 
	 * @param repository A repository that stores coordinates data in PostGIS.
	 * @param sort Determines the order of results for {@link eu.acclimatize.unison.location.CoordinatesStore#sortedFindAll}.
	 */
	public PostGISStore(PostGISCoordinatesRepository repository, Sort sort) {
		this.repository = repository;

		wktR = new WKTReader();
		this.sort = sort;
	}

	@Override
	public void save(double longitude, double latitude, LocationDetails location) {

		Point p;
		try {
			p = (Point) wktR.read("POINT (" + longitude + " " + latitude + ")");
			PostGISCoordinates coord = new PostGISCoordinates(p, location);
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
