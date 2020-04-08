package eu.acclimatize.unison.location.postgis;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import eu.acclimatize.unison.location.CoordinatesSerializer;
import eu.acclimatize.unison.location.CoordinatesStore;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.PointParseException;

/**
 * 
 * A wrapper repository for the {@link PostGISCoordinatesRepository} that hides
 * dependencies on the com.vividsolutions package from classes it is injected
 * to.
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
	 * @param sort       Determines the order of results for
	 *                   {@link eu.acclimatize.unison.location.CoordinatesStore#sortedFindAll}.
	 * @param wktReader  Used to create a point geometry from a Well-Known Text
	 *                   (WKT) description.
	 * 
	 */
	public PostGISStore(PostGISCoordinatesRepository repository, Sort sort, WKTReader wktReader) {
		this.repository = repository;

		wktR = wktReader;
		this.sort = sort;

	}

	@Override
	public void save(double longitude, double latitude, LocationDetails location) {

		String pointWKT = "POINT (" + longitude + " " + latitude + ")";
		try {
			Point p = (Point) wktR.read(pointWKT);
			PostGISCoordinates coord = new PostGISCoordinates(p, location);
			repository.save(coord);
		} catch (ParseException e) {
			throw new PointParseException(pointWKT, e);
		}

	}

	@Override
	public void delete(String locationName) {
		repository.deleteById(locationName);
	}

	@Override
	public List<? extends CoordinatesSerializer> sortedFindAll() {
		return repository.findAll(sort);
	}

}
