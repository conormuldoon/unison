package eu.acclimatize.unison;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonGenerator;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import eu.acclimatize.unison.location.FeatureCollection;
import eu.acclimatize.unison.location.LocationController;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.PointParseException;
import eu.acclimatize.unison.location.PointFeatureSerializer;
import eu.acclimatize.unison.location.postgis.PostGISConfig;
import eu.acclimatize.unison.location.postgis.PostGISCoordinates;
import eu.acclimatize.unison.location.postgis.PostGISCoordinatesRepository;
import eu.acclimatize.unison.location.postgis.PostGISStore;

/**
 * Tests for PostGIS.
 **/
public class PostGISTests {

	/** Tests that the length of the list returned by the location controller. 
	 * @throws IOException 
	 * @throws ParseException **/
	@Test
	public void testLocationList() throws IOException, ParseException {

		PostGISCoordinatesRepository repository = Mockito.mock(PostGISCoordinatesRepository.class);
		List<PostGISCoordinates> list = new ArrayList<>();
		WKTReader wktReader=new WKTReader();
		list.add(new PostGISCoordinates((Point) wktReader.read("Point(-6.224176 53.308366)"),new LocationDetails()));
		Sort sort = Sort.by(Sort.Direction.ASC, "name");
		Mockito.when(repository.findAll(sort)).thenReturn(list);

		PostGISStore postGISStore = new PostGISStore(repository, sort, null);

		LocationController locationController = new LocationController(postGISStore,new PointFeatureSerializer());
		FeatureCollection fc = locationController.location();

		JsonGenerator jg=Mockito.mock(JsonGenerator.class);
		fc.geoJsonSerialize(jg);
		Mockito.verify(jg,Mockito.times(1)).writeStringField("type", "Feature");

	}

	/** Tests that PostGISCoordinates are saved by the repository **/
	@Test
	public void savePoint() {
		PostGISCoordinatesRepository repository = Mockito.mock(PostGISCoordinatesRepository.class);
		PostGISConfig config = new PostGISConfig();
		Sort sort = Sort.by(Sort.Direction.ASC, "name");
		PostGISStore postGISStore = new PostGISStore(repository, sort, config.wktReader());
		postGISStore.save(-6.224176, 53.308366, new LocationDetails());
		
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(PostGISCoordinates.class));
	}


	
	/**
	 * Tests that save catch throws PointParseException when there is a parsing
	 * problem.
	 * 
	 * @throws ParseException The exception is caught within the save method and a
	 *                        PointParseException is thrown.
	 */
	@Test(expected = PointParseException.class)
	public void throwsPointParseException() throws ParseException {
		WKTReader reader = Mockito.mock(WKTReader.class);
		Mockito.when(reader.read(Mockito.anyString())).thenThrow(new ParseException(""));
		PostGISStore postGISStore = new PostGISStore(null, null, reader);
		postGISStore.save(-6.224176, 53.308366, new LocationDetails());
	}
}
