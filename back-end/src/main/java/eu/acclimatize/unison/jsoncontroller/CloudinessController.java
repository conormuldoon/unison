package eu.acclimatize.unison.jsoncontroller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.ItemListFinder;

/**
 * A controller to obtain cloudiness data in a JSON format.
 *
 */
@RestController
public class CloudinessController {

	ItemListFinder cloudinessFinder;
	private static final String MAPPING = "/cloudiness";
	

	/** Creates an instance of CloudinessController.
	 * 
	 * @param cloudinessFinder Used to find an ordered list of cloudiness data.
	 */
	public CloudinessController(ItemListFinder cloudinessFinder) {
		this.cloudinessFinder= cloudinessFinder;
	
	}

	/** Finds a list of Jackson annotated cloudiness data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.CloudinessResult} items.
	 */
	@GetMapping(MAPPING)
	public Iterable<HarmonieItem> cloudiness(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return cloudinessFinder.find(location, fromDate, toDate);
	}

}
