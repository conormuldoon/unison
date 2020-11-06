package eu.acclimatize.unison.jsoncontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import eu.acclimatize.unison.result.PrecipitationResult;

/**
 * 
 * A controller to obtain precipitation data in a JSON format.
 *
 */
@RestController
public class JSONPrecipitationController {

	private ItemListFinder<PrecipitationResult> precipitationFinder;

	/**
	 * Creates an instance of JSONPrecipitationController.
	 * 
	 * @param precipitationFinder Used to find an ordered list of precipitation
	 *                            data.
	 */
	public JSONPrecipitationController(ItemListFinder<PrecipitationResult> precipitationFinder) {
		this.precipitationFinder = precipitationFinder;
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
	public Iterable<? extends HarmonieItem> precipitation(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) {

		List<PrecipitationResult> list = precipitationFinder.find(response, location, fromDate, toDate);
		ArrayList<HarmonieItem> ret = new ArrayList<>();
		
		int n = list.size();
		
		if (n > 0) {
			PrecipitationResult first = list.get(0);
			boolean firstT = first.ternary();
			ret.add(first);
			for (int i = 1; i < n; i++) {
				PrecipitationResult pr = list.get(i);
				if (pr.ternary() == firstT) {
					ret.add(pr);
				}
			}

		}
		
		return ret;

	}

}
