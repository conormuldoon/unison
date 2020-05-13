package eu.acclimatize.unison.location;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

/**
 * A controller for adding or updating a location. In contrast to the
 * {@link AddLocationController}, the controller does not make a harvest data
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
	@RolesAllowed(Constant.ROLL_USER)
	@PutMapping(MappingConstant.LOCATION + "/{" + Constant.LOCATION_NAME + "}")
	public void upsert(@PathVariable(Constant.LOCATION_NAME) String locationName, @RequestBody Location location) {
		if (location.existsIn(locationRepository)) {

			// The owner stored in the repository may be different from authenticated user.
			// The authenticated user is the owner of the location response body.
			String ownerName = locationRepository.findOwner(locationName).get();
			locationService.update(ownerName, location);
		} else {
			locationRepository.save(location);
		}
	}

}
