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
 * A controller for accessing humidity data in a CSV format.
 *
 */
@RestController
public class CSVHumidityController {

	private CSVResponder humidityResponder;

	private static final String CSV_HUMIDITY = "/csvHumidity*";

	/**
	 * Creates an instance of CSVHumidityController.
	 * 
	 * @param humidityResponder A responder that prints humidity data in a CSV format.
	 */
	public CSVHumidityController(CSVResponder humidityResponder) {
		this.humidityResponder=humidityResponder;
	}

	/**
	 * Prints humidity data to the HTTP servlet response object.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the response object.
	 */
	// Specify location, from date, and to date
	@GetMapping(CSV_HUMIDITY)
	public void humidity(@RequestParam(value = Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {
		
		humidityResponder.handleResponse(response, location, fromDate, toDate);

	}

}
