package eu.acclimatize.unison.location;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.ResponseConstant;
import eu.acclimatize.unison.harvester.HarvesterService;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * A controller to add locations to be tracked.
 *
 */
@RestController
public class AddLocationController {

	private LocationRepository locationRepository;

	private HarvesterService harvesterService;

	private Logger logger;

	/**
	 * Creates an instance of AddLocationController.
	 * 
	 * @param locationRepository The repository where locations are stored.
	 * @param harvesterService   Used to request data for the location once added.
	 * @param logger             Logs an error message if the generated XML on the
	 *                           HARMONIE-AROME API server for the location was not
	 *                           found.
	 */
	public AddLocationController(LocationRepository locationRepository, UserRepository userRepository,
			HarvesterService harvesterService, Logger logger, GeometryFactory geometryFactory) {

		this.locationRepository = locationRepository;
		this.harvesterService = harvesterService;
		this.logger = logger;

	}

	/**
	 * Adds a location to the database and requests data for the location from a
	 * HARMONIE-AROME API.
	 * 
	 * @param location The location to be stored in the location repository.
	 * 
	 * @return The {@value eu.acclimatize.unison.ResponseConstant#FAILURE} value if
	 *         failed to add the location, the
	 *         {@value eu.acclimatize.unison.ResponseConstant#SUCCESS} if
	 *         successfully added the location, and the
	 *         {@value eu.acclimatize.unison.ResponseConstant#DATA_NOT_RECEIVED}
	 *         value if the location was added but failed to obtain data from a
	 *         HARMONIE-AROME API.
	 *
	 */
	@PostMapping(Constant.ADD_LOCATION_MAPPING)
	public int addLocation(@RequestBody Location location) {

		if (location.existsIn(locationRepository)) {

			return ResponseConstant.FAILURE;

		} else {

			locationRepository.save(location);

			// Request data
			try {
				if (harvesterService.fetchAndStore(location)) {
					return ResponseConstant.SUCCESS;
				} else {
					return ResponseConstant.DATA_NOT_RECEIVED;
				}
			} catch (LocationRequestException e) {

				logger.log(Level.SEVERE, e.getMessage());
				return ResponseConstant.DATA_NOT_RECEIVED;
			}
		}

	}
}
