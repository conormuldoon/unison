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
 * A controller to obtain low, medium, and high cloud level data in a JSON
 * format.
 *
 */
@RestController
public class JSONCloudLevelController {

	private ItemListFinder<HarmonieItem> cloudLevelFinder;

	/**
	 * Creates an instance of JSONCloudLevelController.
	 * 
	 * @param cloudLevelFinder Used to find an ordered list of cloud level data.
	 */
	public JSONCloudLevelController(ItemListFinder<HarmonieItem> cloudLevelFinder) {
		this.cloudLevelFinder = cloudLevelFinder;
	}

	/**
	 * Finds a list of Jackson annotated cloud level data.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @return A list of {@link eu.acclimatize.unison.result.CloudLevelResult}
	 *         items.
	 */
	@GetMapping(value = MappingConstant.LOCATION_CLOUD_LEVEL, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<HarmonieItem> cloudLevel(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) {

		return cloudLevelFinder.find(response, location, fromDate, toDate);
	}

}
