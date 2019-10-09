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
 * A controller to obtain wind speed data in a JSON format.
 *
 */
@RestController
public class WindSpeedController {

	private ItemListFinder windSpeedFinder;
	private static final String MAPPING = "/windSpeed";

	/**
	 * Creates an instance of WindSpeedController.
	 * 
	 * @param windSpeedFinder Used to find an ordered list of wind speed data.
	 */
	public WindSpeedController(ItemListFinder windSpeedFinder) {
		this.windSpeedFinder = windSpeedFinder;
	}

	/**
	 * Finds a list of Jackson annotated wind speed data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.WindSpeedResult} items.
	 */
	@GetMapping(MAPPING)
	public Iterable<HarmonieItem> windSpeed(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return windSpeedFinder.find(location, fromDate, toDate);
	}

}
