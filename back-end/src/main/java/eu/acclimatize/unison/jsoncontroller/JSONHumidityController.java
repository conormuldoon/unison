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
 * A controller to obtain humidity data in a JSON format.
 *
 */
@RestController
public class JSONHumidityController {

	private ItemListFinder<HarmonieItem> humidityFinder;

	/**
	 * Creates an instance of JSONHumidityController.
	 * 
	 * @param humidityFinder Used to find an ordered list of humidity data.
	 */
	public JSONHumidityController(ItemListFinder<HarmonieItem> humidityFinder) {
		this.humidityFinder = humidityFinder;
	}

	/**
	 * Finds a list of Jackson annotated humidity data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @param response The HTTP servlet response used to add the vary and cache
	 *                 control headers.
	 * @return A list of {@link eu.acclimatize.unison.result.HumidityResult} items.
	 */
	@GetMapping(value = MappingConstant.LOCATION_HUMIDITY, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<HarmonieItem> humidity(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) {

		return humidityFinder.find(response, location, fromDate, toDate);
	}

}
