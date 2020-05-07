package eu.acclimatize.unison;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.location.AddLocationController;
import eu.acclimatize.unison.location.DeleteLocationController;
import eu.acclimatize.unison.location.FeatureCollection;
import eu.acclimatize.unison.location.FeatureCollectionSerializer;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationConfig;
import eu.acclimatize.unison.location.LocationController;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.user.UserRepository;

/**
 * Tests for the controllers in the {@link eu.acclimatize.unison.location}
 * package.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UnisonServerApplication.class,
		LocationConfig.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class LocationTests {

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestRestTemplate template;

	/**
	 * Adds an initial user and location to the database.
	 */
	@Before
	public void addData() {

		TestUtility.saveLocationData(userRepository, locationRepository);

	}

	/**
	 * Removes all data from the location and user repositories.
	 */
	@After
	public void clearData() {
		TestUtility.deleteLocationData(locationRepository, userRepository);
	}

	/**
	 * Tests that a fail response constant is obtained if the location being added
	 * has the same name as an existing location.
	 */
	@Test
	public void locationAlreadyExists() {

		AddLocationController controller = new AddLocationController(locationRepository, userRepository, null, null,
				null);
		Assert.assertEquals(ResponseConstant.FAILURE,
				controller.addLocation(new Location(TestConstant.LOCATION, null, null)));

	}

	/**
	 * Test that a location is added if the request is made by an authenticated user
	 * and the location is not present.
	 * 
	 */
	@Test
	public void validUser() {

		Location location = new Location("New Location", null, new GeometryFactory().createPoint(new Coordinate(0, 0)));

		TestRestTemplate templateWBA = template.withBasicAuth(TestConstant.USERNAME, TestConstant.PASSWORD);
		ResponseEntity<String> result = templateWBA.postForEntity(Constant.ADD_LOCATION_MAPPING, location,
				String.class);

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());

	}

	/**
	 * Tests that a {@value eu.acclimatize.unison.ResponseConstant#FAILURE} response
	 * constant is obtained if the location is not present and request made by a
	 * valid user.
	 */
	@Test
	public void locationNotPresentValidUser() {

		DeleteLocationController controller = new DeleteLocationController(locationRepository, null);
		Assert.assertEquals(ResponseConstant.FAILURE, controller.deleteLocation("Other Location"));
	}

	private void testDelete(String userName, String password, int expectedCount) {
		TestRestTemplate templateWBA = template.withBasicAuth(userName, password);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Constant.DELETE_LOCATION_MAPPING);
		builder.queryParam(Constant.LOCATION, TestConstant.LOCATION);

		templateWBA.delete(builder.build().toUri());
		Assert.assertEquals(expectedCount, locationRepository.count());

	}

	/**
	 * Tests an authenticated user that added a location can delete the location.
	 */
	@Test
	public void deleteValidUser() {

		testDelete(TestConstant.USERNAME, TestConstant.PASSWORD, 0);

	}

	/**
	 * Tests an authenticated user that didn't add a location can't delete the
	 * location.
	 */
	@Test
	public void deleteOtherUser() {

		TestUtility.addUserInformation(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD, userRepository);

		testDelete(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD, 1);
	}

	/**
	 * Tests that the number of features in the GeoJson produced by the location
	 * controller is equal to 1 if only 1 location has been added.
	 * 
	 * @throws IOException Thrown by the JSON generator if there is an I/O error.
	 **/
	@Test
	public void testLocationList() throws IOException {

		Sort sort = Sort.by(Sort.Direction.ASC, "name");

		LocationController locationController = new LocationController(locationRepository, sort);
		FeatureCollection fc = locationController.location();

		JsonGenerator jg = Mockito.mock(JsonGenerator.class);
		fc.geoJSONSerialize(jg);
		Mockito.verify(jg, Mockito.times(1)).writeStringField("type", "Feature");

	}

	/**
	 * Tests the coordinates are serialized in a GeoJSON format correctly.
	 * 
	 * @throws IOException Thrown by the JSON generator if there is an I/O error.
	 **/

	@Test
	public void testSerialization() throws IOException {
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		Location location = TestUtility.createLocation(TestConstant.LOCATION, null, TestConstant.LONGITUDE,
				TestConstant.LATITUDE);
		location.geoJSONSerialize(jsonGenerator);

		jsonGenerator.flush();

		Assert.assertEquals("{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":["
				+ TestConstant.LONGITUDE + "," + TestConstant.LATITUDE + "]},\"properties\":{\"name\":\""
				+ TestConstant.LOCATION + "\"}}", jsonWriter.toString());

	}

	/**
	 * Tests that a feature collection is serialized in a GeoJSON format correctly.
	 * 
	 * * @throws IOException Thrown by the JSON generator if there is an I/O error.
	 * 
	 **/
	@Test
	public void testFCSerialization() throws IOException {

		FeatureCollection featureCollection = new FeatureCollection(new ArrayList<Location>());
		FeatureCollectionSerializer fcs = new FeatureCollectionSerializer();
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		fcs.serialize(featureCollection, jsonGenerator, null);

		jsonGenerator.flush();

		Assert.assertEquals("{\"type\":\"FeatureCollection\",\"features\":[]}", jsonWriter.toString());

	}

	/**
	 * Tests that a list of features is serialized in a GeoJSON format correctly.
	 * 
	 * @throws IOException Thrown by the JSON generator if there is an I/O error.
	 */
	@Test
	public void testFCArraySerialization() throws IOException {

		List<Location> list = new ArrayList<>();

		String[] locArr = { "Dublin", "San Francisco" };

		double[][] coordArr = { { -6.258070, 53.349248 }, { -122.447366, 37.762681 } };
		for (int i = 0; i < locArr.length; i++) {
			Location location = TestUtility.createLocation(locArr[i], null, coordArr[i][0], coordArr[i][1]);
			list.add(location);
		}

		FeatureCollection featureCollection = new FeatureCollection(list);
		FeatureCollectionSerializer fcs = new FeatureCollectionSerializer();
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		fcs.serialize(featureCollection, jsonGenerator, null);

		jsonGenerator.flush();

		String geoStr = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":["
				+ coordArr[0][0] + "," + coordArr[0][1] + "]},\"properties\":{\"name\":\"" + locArr[0]
				+ "\"}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + coordArr[1][0] + ","
				+ coordArr[1][1] + "]},\"properties\":{\"name\":\"" + locArr[1] + "\"}}]}";

		Assert.assertEquals(geoStr, jsonWriter.toString());

	}

}
