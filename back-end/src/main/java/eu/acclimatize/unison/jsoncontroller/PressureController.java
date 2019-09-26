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
 * A controller to obtain pressure data in a JSON format.
 *
 */
@RestController
public class PressureController {

	ItemListFinder pressureFinder;
	private static final String MAPPING = "/pressure";

	/**
	 * Creates an instance of PressureController.
	 * 
	 * @param pressureFinder Used to find an ordered list of pressure data.
	 */
	public PressureController(ItemListFinder pressureFinder) {
		this.pressureFinder=pressureFinder;
	}

	/**
	 * Finds a list of Jackson annotated pressure data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.PressureResult} items.
	 */
	@GetMapping(MAPPING)
	public Iterable<HarmonieItem> pressure(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return pressureFinder.find(location, fromDate, toDate);
	}

}
