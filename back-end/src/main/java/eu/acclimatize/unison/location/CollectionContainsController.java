package eu.acclimatize.unison.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

@RestController
public class CollectionContainsController {

	private LocationRepository locationRepository;

	public CollectionContainsController(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@GetMapping(MappingConstant.CONTAINS)
	public Contains collectionContains(@RequestParam(Constant.LOCATION_NAME) String locationName) {

		boolean exists = locationRepository.existsById(locationName);
		return new Contains(exists);

	}

}
