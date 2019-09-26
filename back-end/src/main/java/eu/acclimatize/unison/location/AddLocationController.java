package eu.acclimatize.unison.location;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.harvester.HarvesterService;
import eu.acclimatize.unison.user.UserService;
import eu.acclimatize.unison.user.UserTask;

/**
 * 
 * A controller to add locations to be tracked.
 *
 */
@RestController
public class AddLocationController {

	private static final String ADD_LOCATION = "/addLocation";
	private static final String LONGITUDE = "longitude";
	private static final String LATITUDE = "latitude";

	private LocationRepository locationRepository;

	private CoordinatesStore store;
	private UserService userService;
	private HarvesterService harvesterService;
	private String uri;

	/**
	 * Creates and instance of AddLocationController.
	 * 
	 * @param locationRepository The repository where locations are stored.
	 * @param store Uses spatial database functionality in storing coordinates.
	 * @param userService A service that enables takes to be executed that require user credentials.
	 * @param harvesterService Used to request data for the location once added.
	 * @param uri The URL template for a HARMONIE-AROME API specified by app.uri in the application properties file.
	 */
	public AddLocationController(LocationRepository locationRepository, CoordinatesStore store, UserService userService,
			HarvesterService harvesterService, @Value("${api.uri}") String uri) {
		this.locationRepository = locationRepository;

		this.store = store;
		this.userService = userService;
		this.harvesterService = harvesterService;
		this.uri = uri;

	}

	/**
	 * Adds a location to the database and requests data for the location from a HARMONIE-AROME API.
	 * 
	 * @param locationName The name of the location to be added.
	 * @param userName The user name credential.
	 * @param password The password d credential.
	 * @param longitude The longitude of the location.
	 * @param latitude The latitude of the location.
	 * @return 0 if failed to add the location, 1 if successfully added the location, 2 if the user provided incorrect credentials, and 3 if the location was added but failed to obtain data from a HARMONIE-AROME API.
	 * @throws CoordinatesParseException Thrown if there was an error parsing the coordinates for the spatial database.
	 */
	@PostMapping(ADD_LOCATION)
	public int addLocation(@RequestParam(Constant.LOCATION) String locationName,
			@RequestParam(Constant.USERNAME) String userName, @RequestParam(Constant.PASSWORD) String password,
			@RequestParam(LONGITUDE) double longitude, @RequestParam(LATITUDE) double latitude)
			throws CoordinatesParseException {

		UserTask task = (user) -> {
			if (locationRepository.existsById(locationName)) {
				return ResponseConstant.FAIL;
			} else {
				String locURI = String.format(uri, latitude, longitude);
				LocationDetails locationDetails = new LocationDetails(locationName, locURI, user);

				store.save(longitude, latitude, locationDetails);

				// Request data
				if (harvesterService.processLocation(locationDetails, Calendar.getInstance())) {
					return ResponseConstant.SUCCESS;
				} else {
					return ResponseConstant.DATA_NOT_RECIEVED;
				}
			}

		};

		return userService.executeTask(userName, password, task);

	}
}
