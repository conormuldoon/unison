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

	public AddLocationController(LocationRepository locationRepository, CoordinatesStore store, UserService userService,
			HarvesterService harvesterService, @Value("${api.uri}") String uri) {
		this.locationRepository = locationRepository;

		this.store = store;
		this.userService = userService;
		this.harvesterService = harvesterService;
		this.uri = uri;

	}

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

				locationRepository.save(locationDetails);

				store.save(locationName, longitude, latitude, locationDetails);

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
