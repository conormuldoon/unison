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
 * A controller to obtain humidity data in a JSON format.
 *
 */
@RestController
public class HumidityController {

	private ItemListFinder humidityFinder;
	private static final String HUMIDITY = "/humidity";

	/**
	 * Creates an instance of HumidityController.
	 * 
	 * @param humidityFinder Used to find an ordered list of humidity data.
	 */
	public HumidityController(ItemListFinder humidityFinder) {
		this.humidityFinder = humidityFinder;
	}

	/**
	 *  Finds a list of Jackson annotated humidity data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.HumidityResult} items.
	 */
	@GetMapping(HUMIDITY)
	public Iterable<HarmonieItem> humidity(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return humidityFinder.find(location, fromDate, toDate);
	}

}
