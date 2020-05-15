package eu.acclimatize.unison.harvester;

import java.security.Principal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

/**
 * 
 * A controller that harvests data for a given location.
 *
 */
@RestController
public class HarvesterController {

	private HarvesterService harvesterService;

	/**
	 * Creates an instance of HarvestController.
	 * 
	 *
	 */
	public HarvesterController(HarvesterService harvesterService) {

		this.harvesterService = harvesterService;

	}

	@PostMapping(MappingConstant.HARVEST)
	public void harvester(Principal principal, @PathVariable(Constant.USER_NAME) String userName,
			@RequestBody String locationName)
			throws HarvestParseException, HarvestRequestException, DocumentNotFoundException {

		// Request data
		harvesterService.fetchAndStore(userName, locationName);

	}

}
