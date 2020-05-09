package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingValueConstant;

/**
 * 
 * A controller for accessing wind direction data in a CSV format.
 *
 */
@RestController
public class CSVWindDirectionController {

	private CSVResponder windDirectionResponder;

	/**
	 * Creates an instance of CSVWindDirectionController.
	 * 
	 * @param windDirectionResponder A responder that prints wind direction data in
	 *                               a CSV format.
	 */
	public CSVWindDirectionController(CSVResponder windDirectionResponder) {
		this.windDirectionResponder = windDirectionResponder;
	}

	/**
	 * Prints wind direction data to the HTTP servlet response object.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the
	 *                     response object.
	 */
	@GetMapping(MappingValueConstant.WIND_DIRECTION)
	public void windDirection(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {

		windDirectionResponder.handleResponse(response, location, fromDate, toDate);
	}

}
