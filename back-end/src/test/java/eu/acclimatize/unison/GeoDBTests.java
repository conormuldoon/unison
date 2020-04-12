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

import eu.acclimatize.unison.location.PointFeature;
import eu.acclimatize.unison.location.FeatureCollection;
import eu.acclimatize.unison.location.FeatureCollectionSerializer;
import eu.acclimatize.unison.location.LocationController;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.PointParseException;
import eu.acclimatize.unison.location.PointFeatureSerializer;
import eu.acclimatize.unison.location.geodb.GeoDBCoordinates;
import eu.acclimatize.unison.location.geodb.GeoDBCoordinatesRepository;
import eu.acclimatize.unison.location.geodb.GeoDBStore;

/**
 * Tests for GeoDB.
 **/
public class GeoDBTests {

	private WKTReader wktReader;

	public GeoDBTests() {
		wktReader = new WKTReader();
	}

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

		list.add(new GeoDBCoordinates((Point) wktReader.read("Point(-6.224176 53.308366)"), new LocationDetails()));
		Sort sort = Sort.by(Sort.Direction.ASC, "name");
		Mockito.when(repository.findAll(sort)).thenReturn(list);

		GeoDBStore geoDBStore = new GeoDBStore(repository, sort, null);

		LocationController locationController = new LocationController(geoDBStore, new PointFeatureSerializer());
		FeatureCollection fc = locationController.location();

		JsonGenerator jg = Mockito.mock(JsonGenerator.class);
		fc.geoJsonSerialize(jg);
		Mockito.verify(jg, Mockito.times(1)).writeStringField("type", "Feature");

	}

	/** Tests that GeoDBCoordinates are saved by the repository **/
	@Test
	public void savePoint() {
		GeoDBCoordinatesRepository repository = Mockito.mock(GeoDBCoordinatesRepository.class);
		Sort sort = Sort.by(Sort.Direction.ASC, "name");
		GeoDBStore geoDBStore = new GeoDBStore(repository, sort, wktReader);
		geoDBStore.save(-6.224176, 53.308366, new LocationDetails());

		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(GeoDBCoordinates.class));
	}

	/** Tests the coordinates are serialized in a GeoJSON format correctly. **/
	@Test
	public void testSerialization() throws IOException, ParseException {
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		double lon = -6.224176;
		double lat = 53.308366;
		String loc = "UCD";
		Point p = (Point) wktReader.read("Point(" + lon + " " + lat + ")");
		PointFeatureSerializer ps = new PointFeatureSerializer();
		ps.serialize(p.getX(), p.getY(), new LocationDetails(loc, null, null), jsonGenerator);

		jsonGenerator.flush();

		Assert.assertEquals("{\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + lon + "," + lat
				+ "]},\"properties\":{\"name\":\"" + loc + "\"},\"type\":\"Feature\"}", jsonWriter.toString());

	}

	/**
	 * Tests that a feature collection is serialized in a GeoJSON format correctly.
	 **/
	@Test
	public void testFCSerialization() throws IOException, ParseException {

		FeatureCollection featureCollection = new FeatureCollection(new ArrayList<PointFeature>(), null);
		FeatureCollectionSerializer fcs = new FeatureCollectionSerializer();
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		fcs.serialize(featureCollection, jsonGenerator, null);

		jsonGenerator.flush();

		Assert.assertEquals("{\"type\":\"FeatureCollection\",\"features\":[]}", jsonWriter.toString());

	}

	private GeoDBCoordinates createCoordinates(String name, double lon, double lat) throws ParseException {

		Point p = (Point) wktReader.read("Point(" + lon + " " + lat + ")");
		GeoDBCoordinates coord = new GeoDBCoordinates(p, new LocationDetails(name, null, null));
		return coord;

	}

	/**
	 * Tests that a list of features is serialized in a GeoJSON format correctly.
	 **/
	@Test
	public void testFCArraySerialization() throws IOException, ParseException {

		List<PointFeature> list = new ArrayList<>();

		String[] locArr = { "UCD", "San Francisco" };
		double[][] coordArr = { { -6.224176, 53.308366 }, { -122.447366, 37.762681 } };
		for (int i = 0; i < locArr.length; i++) {
			list.add(createCoordinates(locArr[i], coordArr[i][0], coordArr[i][1]));
		}

		FeatureCollection featureCollection = new FeatureCollection(list, new PointFeatureSerializer());
		FeatureCollectionSerializer fcs = new FeatureCollectionSerializer();
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		fcs.serialize(featureCollection, jsonGenerator, null);

		jsonGenerator.flush();

		String geoStr = "{\"type\":\"FeatureCollection\",\"features\":[{\"geometry\":{\"type\":\"Point\",\"coordinates\":["
				+ coordArr[0][0] + "," + coordArr[0][1] + "]},\"properties\":{\"name\":\"" + locArr[0]
				+ "\"},\"type\":\"Feature\"},{\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + coordArr[1][0] + ","
				+ coordArr[1][1] + "]},\"properties\":{\"name\":\"" + locArr[1] + "\"},\"type\":\"Feature\"}]}";
		
		Assert.assertEquals(geoStr, jsonWriter.toString());

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
