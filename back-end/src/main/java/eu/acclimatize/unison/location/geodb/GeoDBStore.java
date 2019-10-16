package eu.acclimatize.unison.location.geodb;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import eu.acclimatize.unison.location.CoordinatesParseException;
import eu.acclimatize.unison.location.CoordinatesStore;
import eu.acclimatize.unison.location.LocationDetails;

/**
 * 
 * A wrapper repository for the {@link GeoDBCoordinatesRepository} that hides dependencies on the 
 * org.locationtech package from classes it is injected to.
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
	 * @param sort Determines the order of results for {@link eu.acclimatize.unison.location.CoordinatesStore#sortedFindAll}.
	 * @param wktReader Used to create a point geometry from a Well-Known Text (WKT) description.
	 */
	public GeoDBStore(GeoDBCoordinatesRepository repository, Sort sort, WKTReader wktReader) {
		this.repository = repository;
		wktR = wktReader;

		this.sort = sort;
	}


	@Override
	public void save(double longitude, double latitude, LocationDetails location) {

		Point p;
		try {
			p = (Point) wktR.read("POINT (" + longitude + " " + latitude + ")");
			GeoDBCoordinates coord = new GeoDBCoordinates( p, location);
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
