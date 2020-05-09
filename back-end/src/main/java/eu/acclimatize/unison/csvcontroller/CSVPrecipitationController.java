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
 * A controller for accessing precipitation data in a CSV format.
 *
 */
@RestController
public class CSVPrecipitationController {

	private CSVResponder precipitationResponder;

	/**
	 * Creates an instance of CSVPrecipitationController.
	 * 
	 * @param precipitationResponder A responder that prints precipitation data in a
	 *                               CSV format.
	 */
	public CSVPrecipitationController(CSVResponder precipitationResponder) {
		this.precipitationResponder = precipitationResponder;
	}

	/**
	 * Prints precipitation data to the HTTP servlet response object.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the
	 *                     response object.
	 */
	// Specify location, from date, and to date
	@GetMapping(MappingValueConstant.PRECIPITATION)
	public void precipitation(@RequestParam(value = Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {

		precipitationResponder.handleResponse(response, location, fromDate, toDate);

	}

}
