package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

/**
 * 
 * A controller for accessing low, medium, and high cloud level data in a CSV
 * format.
 *
 */
@RestController
public class CSVCloudLevelController {

	private CSVResponder cloudLevelResponder;

	/**
	 * Creates an instance of CSVCloudLevelController.
	 * 
	 * @param cloudLevelResponder A responder that prints cloud level data in a CSV
	 *                            format.
	 */
	public CSVCloudLevelController(CSVResponder cloudLevelResponder) {
		this.cloudLevelResponder = cloudLevelResponder;
	}

	/**
	 * Prints low, medium, and high cloud level data to the HTTP servlet response
	 * object.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the
	 *                     response object.
	 */
	// Specify location, from date, and to date
	@GetMapping(MappingConstant.LOCATION_CLOUD_LEVEL)
	public void cloudLevel(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {

		cloudLevelResponder.handleResponse(response, location, fromDate, toDate);

	}

}
