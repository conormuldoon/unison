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
 * A controller to obtain wind speed data in CSV format.
 *
 */
@RestController
public class CSVWindSpeedController {

	CSVResponder windSpeedResponder;
	private static final String WINSPEED = "/csvWindSpeed";

	/**
	 * Creates an instance of CSVWindSpeedController.
	 * 
	 * @param windSpeedResponder A responder that writes wind speed data in a CSV format.
	 */
	public CSVWindSpeedController(CSVResponder windSpeedResponder) {
		this.windSpeedResponder=windSpeedResponder;
	}

	/**
	 * Prints wind speed data to the HTTP servlet response object.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the response object.
	 */
	@GetMapping(WINSPEED)
	public void windSpeed(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {

		windSpeedResponder.handleResponse(response, location, fromDate, toDate);
	}

}
