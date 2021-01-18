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
 * A controller for accessing temperature data in a CSV format.
 *
 */
@Controller
public class CSVTemperatureController {

	private CSVResponder temperatureResponder;

	/**
	 * Creates an instance of CSVTemperatureController.
	 * 
	 * @param temperatureResponder A responder that prints temperature data in a CSV
	 *                             format.
	 */
	public CSVTemperatureController(CSVResponder temperatureResponder) {
		this.temperatureResponder = temperatureResponder;
	}

	/**
	 * Prints temperature data to the HTTP servlet response object.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the
	 *                     response object.
	 */
	// Specify location, from date, and to date
	@GetMapping(MappingConstant.LOCATION_TEMPERATURE)
	public void temperature(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {

		temperatureResponder.handleResponse(response, location, fromDate, toDate);

	}

}
