package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

/**
 * 
 * A controller for accessing dew point data in a CSV format.
 *
 */
@Controller
public class CSVDewPointController {

	private CSVResponder dewPointResponder;

	/**
	 * Creates an instance of CSVDewPointController.
	 * 
	 * @param dewPointResponder A responder that prints dew point data in a CSV
	 *                          format.
	 */
	public CSVDewPointController(CSVResponder dewPointResponder) {
		this.dewPointResponder = dewPointResponder;
	}

	/**
	 * Prints dew point data to the HTTP servlet response object.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the
	 *                     response object.
	 */
	// Specify location, from date, and to date
	@GetMapping(MappingConstant.LOCATION_DEW_POINT)
	public void dewPoint(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {

		dewPointResponder.handleResponse(response, location, fromDate, toDate);

	}

}
