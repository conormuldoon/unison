package eu.acclimatize.unison.location;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.harvester.HarvesterController;

/**
 * A controller for adding or updating a location. In contrast to the
 * {@link HarvesterController}, the controller does not make a harvest data
 * request when adding a location.
 *
 */
@RestController
public class UpsertLocationController {

	private LocationRepository locationRepository;
	private LocationService locationService;

	public UpsertLocationController(LocationRepository locationRepository, LocationService locationService) {
		this.locationRepository = locationRepository;
		this.locationService = locationService;

	}

	/**
	 * Add a location if the location does not exist in the location repository or
	 * updates a location if the authenticated user added the location.
	 * 
	 * @param locationName The name of the location to add.
	 * @param location     The location to add.
	 */
	// @RolesAllowed is used when the location is deserialized so is not required
	// here.
	@PutMapping(MappingConstant.LOCATION)
	public String upsert(@RequestBody Location location) {

		Optional<Location> optCurrent = location.findCurrent(locationRepository);

		if (optCurrent.isPresent()) {
			try {
				locationService.replace(optCurrent.get(), location);
			} catch (AccessDeniedException e) {

				throw new LocationUpdateException("A location can only be updated by the user that added it.");
			}
		}

		locationRepository.save(location);

		return MappingConstant.HARVEST;
	}

}
