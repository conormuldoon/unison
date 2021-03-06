package eu.acclimatize.unison;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.location.FeatureCollection;
import eu.acclimatize.unison.location.FeatureCollectionSerializer;
import eu.acclimatize.unison.location.GeoJSONLocationController;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationConstant;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.user.UserRepository;

/**
 * Tests for the controllers in the {@link eu.acclimatize.unison.location}
 * package.
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LocationTests {

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private GeoJSONLocationController geoLocationController;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestRestTemplate template;

	/**
	 * Adds an initial user and location to the database.
	 */
	@BeforeEach
	public void addData() {

		TestUtility.saveLocationData(userRepository, locationRepository);

	}

	/**
	 * Removes all data from the location and user repositories.
	 */
	@AfterEach
	public void clearData() {
		TestUtility.deleteLocationData(locationRepository, userRepository);
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
		templateWBA.put(MappingConstant.LOCATION_COLLECTION, location);

		Assertions.assertEquals(2, locationRepository.count());

	}

	private void testUpdate(String userName, String password, boolean expected) {
		TestUtility.addUserInformation(userName, password, userRepository);
		TestRestTemplate templateWBA = template.withBasicAuth(userName, password);
		Location modifiedLocation = createLocation(TestConstant.LOCATION);
		templateWBA.put(MappingConstant.LOCATION_COLLECTION, modifiedLocation);
		Optional<Location> oLoc = locationRepository.findById(TestConstant.LOCATION);
		Location savedLocation = oLoc.get();
		Assertions.assertEquals(expected, modifiedLocation.equals(savedLocation));

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

		templateWBA.delete(MappingConstant.SPECIFIC_LOCATION, locationName);
		Assertions.assertEquals(expectedCount, locationRepository.count());

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

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FeatureCollection fc = geoLocationController.featureCollection(response);

		JsonGenerator jg = Mockito.mock(JsonGenerator.class);
		fc.geoJSONSerialize(jg);
		Mockito.verify(jg, Mockito.times(1)).writeStringField(Constant.TYPE, LocationConstant.FEATURE);

	}

	/**
	 * Tests a get request for a specific location. Using a mock user as the
	 * TestRestTemplate performs deserialization, which requires an authenticated
	 * user. A user does not need to authenticated to make a get request when not
	 * testing and the system is running as deserialization is not performed.
	 */
	@Test
	@WithMockUser(TestConstant.USERNAME)
	public void singleLocation() {
		ResponseEntity<Location> response = template.getForEntity(
				MappingConstant.LOCATION_COLLECTION + "?" + Constant.LOCATION_NAME + "=" + TestConstant.LOCATION,
				Location.class);
		Assertions.assertNotNull(response.getBody());
	}

	/**
	 * Tests a get request for a location feature collection. Using a mock user as
	 * the TestRestTemplate performs deserialization, which requires an
	 * authenticated user. A user does not need to authenticated to make a get
	 * request when not testing and the system is running as deserialization is not
	 * performed.
	 */
	@Test
	@WithMockUser(TestConstant.USERNAME)
	public void collectionLocation() {
		ResponseEntity<FeatureCollection> response = template.getForEntity(MappingConstant.LOCATION_COLLECTION,
				FeatureCollection.class);
		Assertions.assertNotNull(response.getBody());
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

		location.geoJSONSerialize(jsonGenerator);

		jsonGenerator.flush();

		InputStream is = getClass().getResourceAsStream("/TestPoint.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = jsonWriter.toString();
		System.out.println("\n\n\n" + line);
		Assertions.assertEquals(br.readLine(), jsonWriter.toString());
		br.close();

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

		FeatureCollectionSerializer fcs = new FeatureCollectionSerializer();
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		fcs.serialize(featureCollection, jsonGenerator, null);

		jsonGenerator.flush();

		Assertions.assertEquals("{\"type\":\"FeatureCollection\",\"features\":[]}", jsonWriter.toString());

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

		FeatureCollectionSerializer fcs = new FeatureCollectionSerializer();
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		fcs.serialize(featureCollection, jsonGenerator, null);

		jsonGenerator.flush();

		InputStream is = getClass().getResourceAsStream("/TestCollection.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = jsonWriter.toString();
		System.out.println(line);
		Assertions.assertEquals(br.readLine(), jsonWriter.toString());
		br.close();

	}

}
