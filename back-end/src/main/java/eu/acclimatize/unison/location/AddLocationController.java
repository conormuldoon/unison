package eu.acclimatize.unison.location;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * A controller to add locations to be tracked.In contrast to the
 * {@link UpsertLocationController}, the controller makes a harvest data request
 * when adding a location.
 *
 */
@RestController
public class AddLocationController {

	private LocationRepository locationRepository;

	private HarvesterService harvesterService;

	/**
	 * Creates an instance of AddLocationController.
	 * 
	 * @param locationRepository The repository where locations are stored.
	 * @param harvesterService   Used to request data for the location once added.
	 * @param logger             Used to log harvest location request exceptions.
	 */
	public AddLocationController(LocationRepository locationRepository, UserRepository userRepository,
			HarvesterService harvesterService) {

		this.locationRepository = locationRepository;
		this.harvesterService = harvesterService;

	}

	/**
	 * Adds a location to the database and requests data for the location from a
	 * HARMONIE-AROME API.
	 * 
	 * @param location The location to be stored in the location repository.
	 * 
	 * @return An optional harvesting issue if there was a location harvest request
	 *         exception and an empty optional otherwise.
	 * @throws LocationRequestException
	 * 
	 */
	@RolesAllowed(Constant.ROLL_USER)
	@PostMapping(MappingConstant.LOCATION)
	public void addLocation(@RequestBody Location location) throws LocationRequestException {

		if (location.existsIn(locationRepository)) {

			throw new LocationExistsException("A location with the same name is already being tracked.");

		}
		locationRepository.save(location);

		// Request data
		harvesterService.fetchAndStore(location);

	}

}
