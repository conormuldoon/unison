package eu.acclimatize.unison;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import eu.acclimatize.unison.location.CoordinatesConfig;
import eu.acclimatize.unison.location.LocationController;
import eu.acclimatize.unison.location.geodb.GeoDBCoordinates;
import eu.acclimatize.unison.location.geodb.GeoDBCoordinatesRepository;
import eu.acclimatize.unison.location.geodb.GeoDBStore;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = { UnisonServerApplication.class, CoordinatesConfig.class })
public class GeoDBTest {

	// Tests that the length of the list returned by the location controller.
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
}
