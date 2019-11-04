package eu.acclimatize.unison;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.locationtech.jts.io.WKTReader;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import eu.acclimatize.unison.location.LocationController;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.geodb.GeoDBCoordinates;
import eu.acclimatize.unison.location.geodb.GeoDBCoordinatesRepository;
import eu.acclimatize.unison.location.geodb.GeoDBStore;

/**
 * Tests for GeoDB. Not using the Spring test framework, just mocking, so as
 * that the tests will still run when Spring is configured to use
 * Postgres/PostGIS rather than GeoDB. In that case, classes, such as
 * GeoDBConfig, will be unavailable due to the
 * {@link eu.acclimatize.unison.SpatialExcludeFilter}.
 **/
public class GeoDBTests {

	/** Tests that the length of the list returned by the location controller. **/
	@Test
	public void testLocationList() {

		GeoDBCoordinatesRepository repository = Mockito.mock(GeoDBCoordinatesRepository.class);
		List<GeoDBCoordinates> list = new ArrayList<>();
		list.add(new GeoDBCoordinates());
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		Mockito.when(repository.findAll(sort)).thenReturn(list);

		GeoDBStore geoDBStore = new GeoDBStore(repository, sort, null);

		LocationController locationController = new LocationController(geoDBStore);
		List<? extends Object> locList = locationController.location();

		Assert.assertEquals(1, locList.size());

	}

	/** Tests that GeoDBCoordinates are saved by the repository **/
	@Test
	public void savePoint() {
		GeoDBCoordinatesRepository repository = Mockito.mock(GeoDBCoordinatesRepository.class);
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		GeoDBStore geoDBStore = new GeoDBStore(repository, sort, new WKTReader());
		geoDBStore.save(-6.224176, 53.308366, new LocationDetails());

		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(GeoDBCoordinates.class));
	}

}
