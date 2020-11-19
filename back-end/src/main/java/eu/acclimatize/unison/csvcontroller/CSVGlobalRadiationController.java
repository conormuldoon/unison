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
 * A controller for accessing global radiation data in a CSV format.
 *
 */
@RestController
public class CSVGlobalRadiationController {

	private CSVResponder globalRadiationResponder;

	/**
	 * Creates an instance of CSVGlobalRadiationController.
	 * 
	 * @param dewPointResponder A responder that prints dew point data in a CSV
	 *                          format.
	 */
	public CSVGlobalRadiationController(CSVResponder globalRadiationResponder) {
		this.globalRadiationResponder = globalRadiationResponder;
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
	@GetMapping(MappingConstant.LOCATION_GLOBAL_RADIATION)
	public void dewPoint(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {

		globalRadiationResponder.handleResponse(response, location, fromDate, toDate);

	}

}