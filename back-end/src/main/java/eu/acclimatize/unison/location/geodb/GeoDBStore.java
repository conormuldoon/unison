package eu.acclimatize.unison.location.geodb;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import eu.acclimatize.unison.location.CoordinatesStore;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.PointParseException;

/**
 * 
 * A wrapper repository for the {@link GeoDBCoordinatesRepository} that hides
 * dependencies on the org.locationtech package from classes it is injected to.
 *
 */
@Repository
public class GeoDBStore implements CoordinatesStore {

	private GeoDBCoordinatesRepository repository;

	private WKTReader wktR;

	private Sort sort;

	/**
	 * Creates an instance of GeoDBStore.
	 * 
	 * @param repository A repository that stores coordinates data in GeoDB.
	 * @param sort       Determines the order of results for
	 *                   {@link eu.acclimatize.unison.location.CoordinatesStore#sortedFindAll}.
	 * @param wktReader  Used to create a point geometry from a Well-Known Text
	 *                   (WKT) description.
	 */
	public GeoDBStore(GeoDBCoordinatesRepository repository, Sort sort, WKTReader wktReader) {
		this.repository = repository;
		wktR = wktReader;

		this.sort = sort;

	}

	@Override
	public void save(double longitude, double latitude, LocationDetails location) {

		String pointWKT = "POINT (" + longitude + " " + latitude + ")";

		try {
			Point p = (Point) wktR.read(pointWKT);
			GeoDBCoordinates coord = new GeoDBCoordinates(p, location);
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
	public List<? extends Object> sortedFindAll() {

		return repository.findAll(sort);
	}

}
