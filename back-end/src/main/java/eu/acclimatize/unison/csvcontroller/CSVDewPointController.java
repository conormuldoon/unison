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
public class CSVDewPointController {

	CSVResponder dewPointResponder;

	private static final String CSV_DEWPOINT = "/csvDewPoint";

	public CSVDewPointController(CSVResponder dewPointResponder) {
		this.dewPointResponder = dewPointResponder;
	}

	// Specify location, from date, and to date
	@GetMapping(CSV_DEWPOINT)
	public void dewPoint(@RequestParam(value = Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {
		dewPointResponder.handleResponse(response, location, fromDate, toDate);

	}

}
