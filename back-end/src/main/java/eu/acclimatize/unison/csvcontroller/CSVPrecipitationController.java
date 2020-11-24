package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.PrecipitationResultFilter;

/**
 * 
 * A controller for accessing precipitation data in a CSV format.
 *
 */
@Controller
public class CSVPrecipitationController {

	private CSVResponder precipitationResponder;
	private PrecipitationResultFilter resultFilter;

	/**
	 * Creates an instance of CSVPrecipitationController.
	 * 
	 * @param precipitationResponder A responder that prints precipitation data in a
	 *                               CSV format.
	 */
	public CSVPrecipitationController(CSVResponder precipitationResponder, PrecipitationResultFilter resultFilter) {
		this.precipitationResponder = precipitationResponder;
		this.resultFilter = resultFilter;
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
	@GetMapping(MappingConstant.LOCATION_PRECIPITATION)
	public void precipitation(@PathVariable(Constant.LOCATION_NAME) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {

		List<HarmonieItem> list = resultFilter.filterResults(response, location, fromDate, toDate);

		precipitationResponder.handleResponse(response, toDate, list);

	}

}
