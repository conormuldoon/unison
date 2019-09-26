package eu.acclimatize.unison.location.geodb;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.HourlyWeatherRepository;
import eu.acclimatize.unison.location.CoordinatesStore;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.location.CoordinatesParseException;

@Repository
public class GeoDBStore implements CoordinatesStore {

	private GeoDBCoordinatesRepository repository;

	private WKTReader wktR;

	private Sort sort;

	public GeoDBStore(GeoDBCoordinatesRepository repository, LocationRepository locationRepository,
			HourlyPrecipitationRepository hpr, HourlyWeatherRepository hwr, Sort sort) {
		this.repository = repository;
		wktR = new WKTReader();

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
