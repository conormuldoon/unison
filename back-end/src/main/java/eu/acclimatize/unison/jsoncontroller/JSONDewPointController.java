package eu.acclimatize.unison.jsoncontroller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.ItemListFinder;
import eu.acclimatize.unison.MappingConstant;

/**
 * 
 * A controller to obtain dew point data in a JSON format.
 *
 */
@RestController
public class JSONDewPointController {

	private ItemListFinder<HarmonieItem> dewPointFinder;

	/**
	 * Creates an instance of JSONDewPointController.
	 * 
	 * @param dewPointFinder Used to find an ordered list of dew point data.
	 */
	public JSONDewPointController(ItemListFinder<HarmonieItem> dewPointFinder) {
		this.dewPointFinder = dewPointFinder;
	}

	/**
	 * Finds a list of Jackson annotated dew point data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.DewPointResult} items.
	 */
	@GetMapping(value = MappingConstant.LOCATION_DEW_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<HarmonieItem> dewPoint(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) {

		return dewPointFinder.find(response, location, fromDate, toDate);
	}

}
