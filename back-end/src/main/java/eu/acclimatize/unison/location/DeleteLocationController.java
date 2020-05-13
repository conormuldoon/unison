package eu.acclimatize.unison.location;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

/**
 * 
 * A controller to delete a location being tracked. The precipitation and
 * weather data associated with the location will also be deleted.
 *
 */
@RestController
public class DeleteLocationController {

	private LocationRepository locationRepository;
	private LocationService locationService;

	/**
	 * Creates an instance of DeleteLocationController.
	 * 
	 * @param locationRepository The repository where location data is stored.
	 * @param locationService    The service used to delete locations.
	 */
	public DeleteLocationController(LocationRepository locationRepository, LocationService locationService) {
		this.locationRepository = locationRepository;
		this.locationService = locationService;

	}

	/**
	 * Delete a location and associated harvested data from the database if the
	 * authenticated user added the location.
	 * 
	 * @param locationName The name of the location to delete.
	 * @return The {@value eu.acclimatize.unison.ResponseConstant#FAILURE} value if
	 *         failed to delete the location or the
	 *         {@value eu.acclimatize.unison.ResponseConstant#SUCCESS} value if the
	 *         location was successfully removed.
	 */
	@RolesAllowed(Constant.ROLL_USER)
	@DeleteMapping(MappingConstant.LOCATION + "/{" + Constant.LOCATION_NAME + "}")
	public void deleteLocation(@PathVariable(Constant.LOCATION_NAME) String locationName) {

		Optional<String> optOwner = locationRepository.findOwner(locationName);
		if (optOwner.isPresent()) {
			locationService.delete(locationName, optOwner.get());

		}
	}
}
