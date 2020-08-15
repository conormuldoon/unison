package eu.acclimatize.unison.location.harvester;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;

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
public class HarvestController {

	private HarvesterService harvesterService;
	private LocationRepository locationRepository;

	/**
	 * Creates an instance of HarvestController.
	 * 
	 * 
	 * @param harvesterService   The service used to fetch and store data for the
	 *                           location.
	 * @param locationRepository The repository where locations are stored.
	 */
	public HarvestController(HarvesterService harvesterService, LocationRepository locationRepository) {

		this.harvesterService = harvesterService;
		this.locationRepository = locationRepository;

	}

	/**
	 * Harvests data for a specific location. Only the user that added the location
	 * can asynchronously trigger a harvest for the location.
	 * 
	 * @param locationName The name of the location.
	 * 
	 *                     * @throws HarvestParseException Thrown if there was a SAX
	 *                     exception when parsing.
	 * @throws HarvestRequestException   Thrown if there was an I/O exception when
	 *                                   connecting to the URI.
	 * @throws DocumentNotFoundException Thrown if there was no XML document found
	 *                                   at the URI.
	 * 
	 */
	@RolesAllowed(Constant.ROLL_USER)
	@PostMapping(MappingConstant.SPECIFIC_LOCATION)
	public void harvest(@PathVariable(Constant.LOCATION_NAME) String locationName)
			throws HarvestParseException, HarvestRequestException, DocumentNotFoundException {

		Optional<Location> optLocation = locationRepository.findById(locationName);

		if (optLocation.isEmpty()) {
			throw new LocationNotFoundException(locationName);
		}
		// Request data
		harvesterService.fetchAndStore(optLocation.get());

	}

}
