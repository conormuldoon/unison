package eu.acclimatize.unison;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.location.AddLocationController;
import eu.acclimatize.unison.location.DeserializationException;
import eu.acclimatize.unison.location.FeatureCollection;
import eu.acclimatize.unison.location.FeatureCollectionSerializer;
import eu.acclimatize.unison.location.GeoJSONLocationController;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationConstant;
import eu.acclimatize.unison.location.LocationExistsException;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.location.LocationRequestException;
import eu.acclimatize.unison.location.WeatherProperty;
import eu.acclimatize.unison.user.UserRepository;

/**
 * Tests for the controllers in the {@link eu.acclimatize.unison.location}
 * package.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
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
	 * Tests that a location exists exception is thrown if the location being added
	 * has the same name as an existing location.
	 * 
	 * @throws LocationRequestException Thrown if there was a problem obtaining the
	 *                                  XML for the locations.
	 */
	@Test(expected = LocationExistsException.class)
	public void locationAlreadyExists() throws LocationRequestException {

		AddLocationController controller = new AddLocationController(locationRepository, userRepository, null);
		controller.addLocation(new Location(TestConstant.LOCATION, null, null));

	}

	private Location createLocation(String locationName) {
		return new Location(locationName, null, new GeometryFactory().createPoint(new Coordinate(0, 0)));
	}

	/**
	 * Test that a location is added if the request is made by an authenticated user
	 * and the location is not present.
	 * 
	 */
	@Test
	public void validUser() {

		Location location = createLocation("New Location");

		TestRestTemplate templateWBA = template.withBasicAuth(TestConstant.USERNAME, TestConstant.PASSWORD);
		ResponseEntity<String> result = templateWBA.postForEntity(MappingConstant.LOCATION, location, String.class);

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());

	}

	private void testUpdate(String userName, String password, boolean expected) {
		TestUtility.addUserInformation(userName, password, userRepository);
		TestRestTemplate templateWBA = template.withBasicAuth(userName, password);
		Location modifiedLocation = createLocation(TestConstant.LOCATION);
		templateWBA.put(MappingConstant.LOCATION + "/" + TestConstant.LOCATION, modifiedLocation);
		Optional<Location> oLoc = locationRepository.findById(TestConstant.LOCATION);
		Location savedLocation = oLoc.get();
		Assert.assertEquals(expected, modifiedLocation.equals(savedLocation));

	}

	/**
	 * Tests that a user can update a location that they added.
	 * 
	 */
	@Test
	public void updateLocation() {
		testUpdate(TestConstant.USERNAME, TestConstant.PASSWORD, true);

	}

	/**
	 * Tests that a user cannot update a location that they didn't add.
	 * 
	 */
	@Test
	public void updateLocationOther() {
		testUpdate(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD, false);
	}

	private void testDelete(String userName, String password, int expectedCount, String locationName) {
		TestRestTemplate templateWBA = template.withBasicAuth(userName, password);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(MappingConstant.LOCATION);
		builder.queryParam(Constant.LOCATION_NAME, locationName);

		templateWBA.delete(builder.build().toUri());
		Assert.assertEquals(expectedCount, locationRepository.count());

	}

	/**
	 * Tests that a location is not deleted if the wrong location name is given, but
	 * and request made by a valid user.
	 */
	@Test
	public void locationNotPresentValidUser() {

		testDelete(TestConstant.USERNAME, TestConstant.PASSWORD, 1, "Other Location");
	}

	private void testDelete(String userName, String password, int expectedCount) {
		testDelete(userName, password, expectedCount, TestConstant.LOCATION);
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
	public void locationList() throws IOException {

		Sort sort = Sort.by(Sort.Direction.ASC, "name");

		GeoJSONLocationController locationController = new GeoJSONLocationController(locationRepository, sort);
		FeatureCollection fc = locationController.location();

		JsonGenerator jg = Mockito.mock(JsonGenerator.class);
		fc.geoJSONSerialize(jg, WeatherProperty.values());
		Mockito.verify(jg, Mockito.times(1)).writeStringField(Constant.TYPE, LocationConstant.FEATURE);

	}

	/**
	 * Tests a get request for a specific location. Using a mock user as the
	 * TestRestTemplate performs deserialization, which requires a security context.
	 * A user does not need to authenticated to make a get request when not testing
	 * and the system is running as deserialization is not performed.
	 */
	@Test
	@WithMockUser(TestConstant.USERNAME)
	public void singleLocation() {
		ResponseEntity<Location> response = null;
		try {
			response = template.getForEntity(MappingConstant.LOCATION + "/" + TestConstant.LOCATION, Location.class);
		} catch (NullPointerException | NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail();
		}

		Assert.assertNotNull(response.getBody());
	}

	/**
	 * Tests a get request for a location feature collection. Using a mock user as
	 * the TestRestTemplate performs deserialization, which requires a security
	 * context. A user does not need to authenticated to make a get request when not
	 * testing and the system is running as deserialization is not performed.
	 */
	@Test
	@WithMockUser(TestConstant.USERNAME)
	public void collectionLocation() {
		ResponseEntity<FeatureCollection> response = null;

		try {
			response = template.getForEntity(MappingConstant.LOCATION, FeatureCollection.class);
		} catch (DeserializationException e) {
			e.printStackTrace();
			Assert.fail();
		}

		Assert.assertNotNull(response.getBody());
	}

	/**
	 * Tests the coordinates are serialized in a GeoJSON format correctly.
	 * 
	 * @throws IOException Thrown by the JSON generator if there is an I/O error.
	 **/

	@Test
	public void serialization() throws IOException {
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		Location location = TestUtility.createLocation(TestConstant.LOCATION, null, TestConstant.LONGITUDE,
				TestConstant.LATITUDE);
		WeatherProperty[] weatherProperty = {};
		location.geoJSONSerialize(jsonGenerator, weatherProperty);

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
	public void collectionSerialization() throws IOException {

		FeatureCollection featureCollection = new FeatureCollection(new ArrayList<Location>());

		FeatureCollectionSerializer fcs = new FeatureCollectionSerializer(null);
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
	public void collectionArraySerialization() throws IOException {

		List<Location> list = new ArrayList<>();

		String[] locArr = { "Dublin", "San Francisco" };

		double[][] coordArr = { { -6.258070, 53.349248 }, { -122.447366, 37.762681 } };
		for (int i = 0; i < locArr.length; i++) {
			Location location = TestUtility.createLocation(locArr[i], null, coordArr[i][0], coordArr[i][1]);
			list.add(location);
		}

		FeatureCollection featureCollection = new FeatureCollection(list);

		WeatherProperty[] weatherProperty = {};
		FeatureCollectionSerializer fcs = new FeatureCollectionSerializer(weatherProperty);
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
