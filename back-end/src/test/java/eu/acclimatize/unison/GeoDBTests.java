package eu.acclimatize.unison;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.location.FeatureCollection;
import eu.acclimatize.unison.location.LocationController;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.PointParseException;
import eu.acclimatize.unison.location.PointSerializer;
import eu.acclimatize.unison.location.geodb.GeoDBCoordinates;
import eu.acclimatize.unison.location.geodb.GeoDBCoordinatesRepository;
import eu.acclimatize.unison.location.geodb.GeoDBStore;

/**
 * Tests for GeoDB.
 **/
public class GeoDBTests {

	/**
	 * Tests that the length of the list returned by the location controller.
	 * 
	 * @throws IOException
	 * @throws ParseException 
	 **/
	@Test
	public void testLocationList() throws IOException, ParseException {

		GeoDBCoordinatesRepository repository = Mockito.mock(GeoDBCoordinatesRepository.class);
		List<GeoDBCoordinates> list = new ArrayList<>();
		WKTReader wktReader=new WKTReader();
		list.add(new GeoDBCoordinates((Point) wktReader.read("Point(-6.224176 53.308366)"),null));
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		Mockito.when(repository.findAll(sort)).thenReturn(list);

		GeoDBStore geoDBStore = new GeoDBStore(repository, sort, null);

		LocationController locationController = new LocationController(geoDBStore, new PointSerializer());
		FeatureCollection fc = locationController.location();

		JsonGenerator jg = Mockito.mock(JsonGenerator.class);
		fc.geoJsonSerialize(jg);
		Mockito.verify(jg, Mockito.times(1)).writeStringField("type", "Feature");

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

	/** Tests the coordinates are serialized in a GeoJSON format correctly. **/
//	@Test
//	public void testSerialization() throws IOException, ParseException {
//		Writer jsonWriter = new StringWriter();
//		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
//		SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();
//		WKTReader wktReader = new WKTReader();
//		Point p = (Point) wktReader.read("Point(-6.224176 53.308366)");
//		new GeoDBPointSerializer().serialize(p, jsonGenerator, serializerProvider);
//		jsonGenerator.flush();
//
//		Assert.assertEquals("{\"type\":\"Point\",\"coordinates\":[-6.224176,53.308366]}", jsonWriter.toString());
//
//	}

	/** Tests the coordinates are serialized in a GeoJSON format correctly. **/
	@Test
	public void testSerialization() throws IOException, ParseException {
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		WKTReader wktReader = new WKTReader();
		double lon = -6.224176;
		double lat = 53.308366;
		String loc = "UCD";
		Point p = (Point) wktReader.read("Point(" + lon + " " + lat + ")");
		PointSerializer ps = new PointSerializer();
		ps.serialize(p.getX(), p.getY(), loc, jsonGenerator);

		jsonGenerator.flush();

		Assert.assertEquals("{\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + lon + "," + lat
				+ "]},\"properties\":{\"name\":\"" + loc + "\"},\"type\":\"Feature\"}", jsonWriter.toString());

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
		GeoDBStore geoDBStore = new GeoDBStore(null, null, reader);
		geoDBStore.save(-6.224176, 53.308366, new LocationDetails());
	}

}
