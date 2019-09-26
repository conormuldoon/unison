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
 * A controller to obtain fog data in a JSON format.
 *
 */
@RestController
public class FogController {

	ItemListFinder fogFinder;
	private static final String FOG = "/fog";

	/**
	 * Creates an instance of FogController.
	 * 
	 * @param fogFinder Used to find an ordered list of fog data.
	 */
	public FogController(ItemListFinder fogFinder) {
		this.fogFinder=fogFinder;
	}

	/**
	 *  Finds a list of Jackson annotated fog data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.FogResult} items.
	 */
	@GetMapping(FOG)
	public Iterable<HarmonieItem> fog(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return fogFinder.find(location, fromDate, toDate);
	}

}
