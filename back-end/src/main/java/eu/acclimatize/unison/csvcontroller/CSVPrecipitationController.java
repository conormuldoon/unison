package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;

@RestController
public class CSVPrecipitationController {

	CSVResponder precipitationResponder;

	private static final String CSV_PRECIPITATION = "/csvPrecipitation";

	public CSVPrecipitationController(CSVResponder precipitationResponder) {
		this.precipitationResponder = precipitationResponder;
	}

	// Specify location, from date, and to date
	@GetMapping(CSV_PRECIPITATION)
	public void precipitation(@RequestParam(value = Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {
		precipitationResponder.handleResponse(response, location, fromDate, toDate);

	}

}
