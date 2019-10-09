package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;

/**
 * 
 * A controller for accessing temperature data in a CSV format.
 *
 */
@RestController
public class CSVTemperatureController {

	CSVResponder temperatureResponder;

	private static final String CSV_TEMP = "/csvTemperature";

	/**
	 * Creates an instance of CSVTemperatureController.
	 * 
	 * @param temperatureResponder A responder that prints temperature data in a CSV format.
	 */
	public CSVTemperatureController(CSVResponder temperatureResponder) {
		this.temperatureResponder = temperatureResponder;
	}

	/**
	 * Prints temperature data to the HTTP servlet response object.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the response object.
	 */
	// Specify location, from date, and to date
	@GetMapping(CSV_TEMP)
	public void temperature(@RequestParam(value = Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {
		// TemperatureResult.printTitle(response.getWriter());
		temperatureResponder.handleResponse(response, location, fromDate, toDate);

	}

}
