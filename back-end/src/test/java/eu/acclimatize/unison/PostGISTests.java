package eu.acclimatize.unison;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import eu.acclimatize.unison.location.LocationController;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.postgis.PostGISConfig;
import eu.acclimatize.unison.location.postgis.PostGISCoordinates;
import eu.acclimatize.unison.location.postgis.PostGISCoordinatesRepository;
import eu.acclimatize.unison.location.postgis.PostGISStore;

/**
 * Tests for PostGIS.
 **/
public class PostGISTests {

	/** Tests that the length of the list returned by the location controller. **/
	@Test
	public void testLocationList() {

		PostGISCoordinatesRepository repository = Mockito.mock(PostGISCoordinatesRepository.class);
		List<PostGISCoordinates> list = new ArrayList<>();
		list.add(new PostGISCoordinates());
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		Mockito.when(repository.findAll(sort)).thenReturn(list);

		PostGISStore geoDBStore = new PostGISStore(repository, sort, null, null);

		LocationController locationController = new LocationController(geoDBStore);
		List<? extends Object> locList = locationController.location();

		Assert.assertEquals(1, locList.size());

	}

	/** Tests that PostGISCoordinates are saved by the repository **/
	@Test
	public void savePoint() {
		PostGISCoordinatesRepository repository = Mockito.mock(PostGISCoordinatesRepository.class);
		PostGISConfig config = new PostGISConfig();
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		PostGISStore postGISStore = new PostGISStore(repository, sort, config.wktReader(), null);
		postGISStore.save(-6.224176, 53.308366, new LocationDetails());
		
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(PostGISCoordinates.class));
	}

}
