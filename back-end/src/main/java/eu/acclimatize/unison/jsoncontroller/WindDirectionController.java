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
 * A controller to obtain wind direction data in a JSON format.
 *
 */
@RestController
public class WindDirectionController {

	private ItemListFinder windDirectionFinder;
	private static final String MAPPING = "/windDirection";

	/**
	 * Creates an instance of WindDirectionController.
	 * 
	 * @param windDirectionFinder  Used to find an ordered list of wind direction data.
	 */
	public WindDirectionController(ItemListFinder windDirectionFinder) {
		this.windDirectionFinder = windDirectionFinder;
	}

	/**
	 * Finds a list of Jackson annotated wind direction data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.WindDirectionResult} items.
	 */
	@GetMapping(MAPPING)
	public Iterable<HarmonieItem> windDirection(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return windDirectionFinder.find(location, fromDate, toDate);
	}

}
