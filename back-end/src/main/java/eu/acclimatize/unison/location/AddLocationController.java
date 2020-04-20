package eu.acclimatize.unison.location;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.ResponseConstant;
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

	private UserService userService;
	private HarvesterService harvesterService;

	private Logger logger;

	private GeometryFactory geometryFactory;

	/**
	 * Creates an instance of AddLocationController.
	 * 
	 * @param locationRepository The repository where locations are stored.
	 * @param userService        A service that enables takes to be executed that
	 *                           require user credentials.
	 * @param harvesterService   Used to request data for the location once added.
	 * @param logger             Logs an error message if the generated XML on the
	 *                           HARMONIE-AROME API server for the location was not
	 *                           found.
	 * @param geometryFactory    A factory used to create points.
	 */
	public AddLocationController(LocationRepository locationRepository, UserService userService,
			HarvesterService harvesterService, Logger logger,
			GeometryFactory geometryFactory) {
		this.locationRepository = locationRepository;

		this.userService = userService;
		this.harvesterService = harvesterService;
		this.logger = logger;
		this.geometryFactory = geometryFactory;

	}

	/**
	 * Adds a location to the database and requests data for the location from a
	 * HARMONIE-AROME API.
	 * 
	 * @param locationName The name of the location to be added.
	 * @param userName     The user name credential.
	 * @param password     The password d credential.
	 * @param longitude    The longitude of the location.
	 * @param latitude     The latitude of the location.
	 * @return 0 if failed to add the location, 1 if successfully added the
	 *         location, 2 if the user provided incorrect credentials, and 3 if the
	 *         location was added but failed to obtain data from a HARMONIE-AROME
	 *         API.
	 *
	 */
	@PostMapping(ADD_LOCATION)
	public int addLocation(@RequestParam(Constant.LOCATION) String locationName,
			@RequestParam(Constant.USERNAME) String userName, @RequestParam(Constant.PASSWORD) String password,
			@RequestParam(LONGITUDE) double longitude, @RequestParam(LATITUDE) double latitude) {

		UserTask task = user -> {
			if (locationRepository.existsById(locationName)) {
				return ResponseConstant.FAIL;
			} else {
				
				Location location = new Location(locationName,  user,
						geometryFactory.createPoint(new Coordinate(longitude, latitude)));

				locationRepository.save(location);

				// Request data
				try {
					if (harvesterService.fetchAndStore(location)) {
						return ResponseConstant.SUCCESS;
					} else {
						return ResponseConstant.DATA_NOT_RECIEVED;
					}
				} catch (LocationRequestException e) {

					logger.log(Level.SEVERE, e.getMessage());
					return ResponseConstant.DATA_NOT_RECIEVED;
				}
			}

		};

		return userService.executeTask(userName, password, task);

	}
}
