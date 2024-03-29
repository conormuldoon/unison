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
 * A controller to obtain wind speed data in a JSON format.
 *
 */
@RestController
public class JSONWindSpeedController {

	private ItemListFinder<HarmonieItem> windSpeedFinder;

	/**
	 * Creates an instance of WindSpeedController.
	 * 
	 * @param windSpeedFinder Used to find an ordered list of wind speed data.
	 */
	public JSONWindSpeedController(ItemListFinder<HarmonieItem> windSpeedFinder) {
		this.windSpeedFinder = windSpeedFinder;
	}

	/**
	 * Finds a list of Jackson annotated wind speed data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @param response The HTTP servlet response used to add the vary and cache
	 *                 control headers.
	 * @return A list of {@link eu.acclimatize.unison.result.WindSpeedResult} items.
	 */
	@GetMapping(value = MappingConstant.LOCATION_WIND_SPEED, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<HarmonieItem> windSpeed(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) {

		return windSpeedFinder.find(response, location, fromDate, toDate);
	}

}
