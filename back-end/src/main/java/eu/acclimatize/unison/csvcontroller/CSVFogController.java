package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.result.FogResult;

/**
 * 
 * A controller for accessing fog data in a CSV format.
 *
 */
@RestController
public class CSVFogController {

	CSVResponder fogResponder;

	private static final String CSV_Fog = "/csvFog";

	/**
	 * Creates an instance of CSVFogController.
	 * 
	 * @param fogResponder A responder that prints fog data in a CSV format.
	 */
	public CSVFogController(CSVResponder fogResponder) {
		this.fogResponder=fogResponder;
	}

	/**
	 * Prints fog data to the HTTP servlet response object.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the response object.
	 */
	// Specify location, from date, and to date
	@GetMapping(CSV_Fog)
	public void fog(@RequestParam(value = Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {
		
		FogResult.printTitle(response.getWriter());
		fogResponder.handleResponse(response, location, fromDate, toDate);

	}

}
