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
 * A controller to obtain global radiation data in a JSON format.
 *
 */
@RestController
public class JSONGlobalRadiationController {

	private ItemListFinder<HarmonieItem> globalRadiationFinder;

	/**
	 * Creates an instance of JSONGlobalRadiationController.
	 * 
	 * @param cloudinessFinder Used to find an ordered list of global radiation
	 *                         data.
	 */
	public JSONGlobalRadiationController(ItemListFinder<HarmonieItem> globalRadiationFinder) {
		this.globalRadiationFinder = globalRadiationFinder;

	}

	/**
	 * Finds a list of Jackson annotated global radiation data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.CloudinessResult}
	 *         items.
	 */
	@GetMapping(value = MappingConstant.LOCATION_GLOBAL_RADIATION, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<HarmonieItem> cloudiness(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) {

		return globalRadiationFinder.find(response, location, fromDate, toDate);
	}

}
