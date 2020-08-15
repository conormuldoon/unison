package eu.acclimatize.unison.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

/**
 * 
 * A controller for checking whether the location collection
 * contains a specified location.
 *
 */
@RestController
public class CollectionContainsController {

	private LocationRepository locationRepository;

	/**
	 * Creates an instance of CollectionContainsController.
	 * 
	 * @param locationRepository The repository where locations are stored.
	 */
	public CollectionContainsController(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}
	

	/**
	 * Checks where the location repository contains the named location.
	 * 
	 * @param locationName The location to check for.
	 * @return A true value if the collection contains location and a false value otherwise.
	 */
	@GetMapping(MappingConstant.CONTAINS)
	public Contains collectionContains(@RequestParam(Constant.LOCATION_NAME) String locationName) {
 
		boolean exists = locationRepository.existsById(locationName);
		return new Contains(exists);

	}

}
