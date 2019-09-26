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
 * 
 * A controller to obtain dew point data in a JSON format.
 *
 */
@RestController
public class DewPointController {

	ItemListFinder dewPointFinder;
	private static final String MAPPING = "/dewPoint";

	/**
	 * Creates an instance of DewPointController.
	 * 
	 * @param dewPointFinder Used to find an ordered list of dew point data.
	 */
	public DewPointController(ItemListFinder dewPointFinder) {
		this.dewPointFinder = dewPointFinder;
	}

	/**
	 *  Finds a list of Jackson annotated dew point data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.DewPointResult} items.
	 */
	@GetMapping(MAPPING)
	public Iterable<HarmonieItem> dewPoint(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return dewPointFinder.find(location, fromDate, toDate);
	}

}
