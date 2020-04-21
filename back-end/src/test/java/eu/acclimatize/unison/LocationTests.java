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
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.harvester.HarvesterService;
import eu.acclimatize.unison.location.AddLocationController;
import eu.acclimatize.unison.location.DeleteLocationController;
import eu.acclimatize.unison.location.FeatureCollection;
import eu.acclimatize.unison.location.FeatureCollectionSerializer;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationConfig;
import eu.acclimatize.unison.location.LocationController;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.location.LocationRequestException;
import eu.acclimatize.unison.user.UserInformation;
import eu.acclimatize.unison.user.UserRepository;
import eu.acclimatize.unison.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UnisonServerApplication.class, LocationConfig.class })

public class LocationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Add initial data to the database.
	 */
	@Before
	public void addData() {

		TestUtility.saveLocationData(userRepository, locationRepository);

	}

	/**
	 * Removes data from the database.
	 */
	@After
	public void clearData() {
		TestUtility.deleteLocationData(locationRepository, userRepository);
	}

	private UserInformation addUser(String userName, String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserInformation userInfo = new UserInformation(userName, passwordEncoder.encode(password));
		userRepository.save(userInfo);
		return userInfo;
	}

	/**
	 * Tests that a fail response constant is obtained if the location being added
	 * has the same name as an existing location.
	 */
	@Test
	public void locationAlreadyExists() {

		AddLocationController controller = new AddLocationController(locationRepository, userService, null, null, null);
		Assert.assertEquals(ResponseConstant.FAIL,
				controller.addLocation(TestConstant.LOCATION, TestConstant.USERNAME, TestConstant.PASSWORD, 0, 0));

	}

	/**
	 * Tests that a response constant of incorrect credentials is returned if
	 * incorrect password is provided.
	 */
	@Test
	public void invalidUser() {

		AddLocationController controller = new AddLocationController(locationRepository, userService, null, null, null);
		Assert.assertEquals(ResponseConstant.INCORRECT_CREDENTIALS,
				controller.addLocation(TestConstant.LOCATION, TestConstant.USERNAME, "abc", 0, 0));
	}

	/**
	 * Test that a success response constant is obtained when a new location is
	 * added by a valid user.
	 */
	@Test
	public void validUser() throws LocationRequestException {

		HarvesterService hs = Mockito.mock(HarvesterService.class);

		Mockito.when(hs.fetchAndStore(Mockito.any(Location.class))).thenReturn(true);

		AddLocationController controller = new AddLocationController(locationRepository, userService, hs, null,
				new GeometryFactory());
		Assert.assertEquals(ResponseConstant.SUCCESS,
				controller.addLocation("newLocation", TestConstant.USERNAME, TestConstant.PASSWORD, 0, 0));

	}

	/**
	 * Tests that fail response constant obtained if the location is not present and
	 * request made by a valid user.
	 */
	@Test
	public void locationNotPresentValidUser() {

		DeleteLocationController controller = new DeleteLocationController(locationRepository, null, null, userService);
		Assert.assertEquals(ResponseConstant.FAIL,
				controller.deleteLocation("otherLocation", TestConstant.USERNAME, TestConstant.PASSWORD));
	}

	// Test when user does not exist
	@Test
	public void invalidLocationOwner() {

		DeleteLocationController controller = new DeleteLocationController(locationRepository, null, null, userService);
		Assert.assertEquals(ResponseConstant.INCORRECT_CREDENTIALS,
				controller.deleteLocation(TestConstant.LOCATION, "otherUser", null));
	}

	// Testing delete when user exists, but other user added location
	@Test
	public void otherUserAddedLocation() {

		String ou = "otherUser";
		String op = "otherPassword";
		addUser(ou, op);

		DeleteLocationController controller = new DeleteLocationController(locationRepository, null, null, userService);
		Assert.assertEquals(ResponseConstant.NOT_OWNER, controller.deleteLocation(TestConstant.LOCATION, ou, op));

	}

	/**
	 * Tests that the length of the list returned by the location controller is
	 * equal to 1 if only 1 location has been added.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 **/
	@Test
	public void testLocationList() throws IOException {

		Sort sort = Sort.by(Sort.Direction.ASC, "name");

		LocationController locationController = new LocationController(locationRepository, sort);
		FeatureCollection fc = locationController.location();

		JsonGenerator jg = Mockito.mock(JsonGenerator.class);
		fc.geoJsonSerialize(jg);
		Mockito.verify(jg, Mockito.times(1)).writeStringField("type", "Feature");

	}

	/** Tests the coordinates are serialized in a GeoJSON format correctly. **/
	@Test
	public void testSerialization() throws IOException {
		Writer jsonWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);

		Location location = TestUtility.createLocation(TestConstant.LOCATION, null, TestConstant.LONGITUDE,
				TestConstant.LATITUDE);
		location.serialize(jsonGenerator);

		jsonGenerator.flush();

		Assert.assertEquals("{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":["
				+ TestConstant.LONGITUDE + "," + TestConstant.LATITUDE + "]},\"properties\":{\"name\":\""
				+ TestConstant.LOCATION + "\"}}", jsonWriter.toString());

	}

	/**
	 * Tests that a feature collection is serialized in a GeoJSON format correctly.
	 **/
	@Test
	public void testFCSerialization() throws IOException, ParseException {

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
	 **/
	@Test
	public void testFCArraySerialization() throws IOException, ParseException {

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
