package eu.acclimatize.unison.jsoncontroller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.ItemListFinder;
import eu.acclimatize.unison.MappingValueConstant;

/**
 * 
 * A controller to obtain precipitation data in a JSON format.
 *
 */
@RestController
public class JSONPrecipitationController {

	private ItemListFinder precipitationFinder;

	/**
	 * Creates an instance of JSONPrecipitationController.
	 * 
	 * @param precipitationFinder Used to find an ordered list of precipitation
	 *                            data.
	 */
	public JSONPrecipitationController(ItemListFinder precipitationFinder) {
		this.precipitationFinder = precipitationFinder;
	}

	/**
	 * Finds a list of Jackson annotated preciptation data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.PrecipitationResult}
	 *         items.
	 */
	@GetMapping(value = MappingValueConstant.PRECIPITATION, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<HarmonieItem> precipitation(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return precipitationFinder.find(location, fromDate, toDate);

	}

}
