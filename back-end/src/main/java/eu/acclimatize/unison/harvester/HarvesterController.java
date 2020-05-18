package eu.acclimatize.unison.harvester;

import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationNotFoundException;
import eu.acclimatize.unison.location.LocationRepository;

/**
 * 
 * A controller that harvests data for a given location.
 *
 */
@RestController
public class HarvesterController {

	private HarvesterService harvesterService;
	private LocationRepository locationRepository;

	/**
	 * Creates an instance of HarvestController.
	 * 
	 *
	 */
	public HarvesterController(HarvesterService harvesterService, LocationRepository locationRepository) {

		this.harvesterService = harvesterService;
		this.locationRepository = locationRepository;

	}

	@PostMapping(MappingConstant.HARVEST)
	public void harvester(
			@PathVariable(Constant.LOCATION_NAME) String locationName)
			throws HarvestParseException, HarvestRequestException, DocumentNotFoundException {

		Optional<Location> optLocation = locationRepository.findById(locationName);

		if (optLocation.isEmpty()) {
			throw new LocationNotFoundException(locationName);
		}
		// Request data
		harvesterService.fetchAndStore(optLocation.get());

	}

}
