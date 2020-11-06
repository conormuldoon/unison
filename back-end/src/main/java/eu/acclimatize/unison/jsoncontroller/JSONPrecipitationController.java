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
import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.PrecipitationResultFilter;

/**
 * 
 * A controller to obtain precipitation data in a JSON format.
 *
 */
@RestController
public class JSONPrecipitationController {

	private PrecipitationResultFilter resultFilter;

	/**
	 * Creates an instance of JSONPrecipitationController.
	 * 
	 * @param restultFilter Used to find an ordered list of precipitation data
	 *                      containing either ternary or single value precipitation
	 *                      values.
	 */
	public JSONPrecipitationController(PrecipitationResultFilter resultFilter) {
		this.resultFilter = resultFilter;
	}

	/**
	 * Finds a list of Jackson annotated precipitation data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.PrecipitationResult}
	 *         items.
	 */
	@GetMapping(value = MappingConstant.LOCATION_PRECIPITATION, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<HarmonieItem> precipitation(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) {
		
		return resultFilter.filterResults(response, location, fromDate, toDate);
	
	}

}
