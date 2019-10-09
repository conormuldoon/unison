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
 * A controller to obtain temperature data in a JSON format.
 *
 */
@RestController
public class TemperatureController {

	private ItemListFinder temperatureFinder;
	private static final String TEMP = "/temperature";

	/**
	 * Creates an instance of TemperatureController.
	 * 
	 * @param temperatureFinder Used to find an ordered list of pressure data.
	 */
	public TemperatureController(ItemListFinder temperatureFinder) {
		this.temperatureFinder = temperatureFinder;
	}

	/**
	 * Finds a list of Jackson annotated temperature data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.TemperatureResult} items.
	 */
	@GetMapping(TEMP)
	public Iterable<HarmonieItem> temperature(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return temperatureFinder.find(location, fromDate, toDate);
	}

}
