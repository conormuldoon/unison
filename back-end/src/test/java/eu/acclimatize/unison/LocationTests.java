package eu.acclimatize.unison;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import eu.acclimatize.unison.harvester.HarvesterService;
import eu.acclimatize.unison.location.AddLocationController;
import eu.acclimatize.unison.location.CoordinatesParseException;
import eu.acclimatize.unison.location.CoordinatesStore;
import eu.acclimatize.unison.location.DeleteLocationController;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.location.ResponseConstant;
import eu.acclimatize.unison.user.UserInformation;
import eu.acclimatize.unison.user.UserRepository;
import eu.acclimatize.unison.user.UserService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationTests {

	final static private String LOCATION = "UCD";
	final static private String USER = "conor";
	final static private String PWD = "pwd";
	final static private String URI = "https://api.met.no/weatherapi/locationforecast/1.9/?lat=%f&lon=%f";

	@Autowired
	UserService userService;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	UserRepository userRepository;

	@Before
	public void addData() {
		UserInformation userInfo = addUser(USER, PWD);
		locationRepository.save(new LocationDetails(LOCATION, null, userInfo));

	}

	private UserInformation addUser(String userName, String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserInformation userInfo = new UserInformation(USER, passwordEncoder.encode(PWD));
		userRepository.save(userInfo);
		return userInfo;
	}

	@Test
	public void locationAlreadyExists() throws CoordinatesParseException {

		AddLocationController controller = new AddLocationController(locationRepository,null,
				userService, null, URI);
		Assert.assertEquals(ResponseConstant.FAIL, controller.addLocation(LOCATION, USER, PWD, 0, 0));

	}

	@Test
	public void invalidUser() throws CoordinatesParseException {

		AddLocationController controller = new AddLocationController(locationRepository, null, userService, null, null);
		Assert.assertEquals(ResponseConstant.INCORRECT_CREDENTIALS,
				controller.addLocation(LOCATION, USER, "abc", 0, 0));
	}

	@Test
	public void validUser() throws CoordinatesParseException {

		HarvesterService hs = Mockito.mock(HarvesterService.class);

		CoordinatesStore store = Mockito.mock(CoordinatesStore.class);

		Mockito.when(hs.processLocation(Mockito.any(LocationDetails.class), Mockito.any(Calendar.class)))
				.thenReturn(true);

		AddLocationController controller = new AddLocationController(locationRepository, store, userService, hs, URI);
		Assert.assertEquals(ResponseConstant.SUCCESS, controller.addLocation("newLocation", USER, PWD, 0, 0));

	}

	@Test
	public void locationNotPresentValidUser() {

		DeleteLocationController controller = new DeleteLocationController(locationRepository, null, null, null,
				userService);
		Assert.assertEquals(ResponseConstant.FAIL, controller.deleteLocation("otherLocation", USER, PWD));
	}

	// Test when user does not exist
	@Test
	public void invalidLocationOwner() {

		DeleteLocationController controller = new DeleteLocationController(locationRepository, null, null, null,
				userService);
		Assert.assertEquals(ResponseConstant.INCORRECT_CREDENTIALS,
				controller.deleteLocation(LOCATION, "otherUser", null));
	}

	// Testing delete when user exists, but other user added location
	@Test
	public void otherUserAddedLocation() {

		String ou = "otherUser";
		String op = "otherPassword";
		addUser(ou, op);

		DeleteLocationController controller = new DeleteLocationController(locationRepository, null, null, null,
				userService);
		Assert.assertEquals(ResponseConstant.INCORRECT_CREDENTIALS, controller.deleteLocation(LOCATION, ou, op));

	}

}
