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
public class CSVPressureController {

	CSVResponder pressureResponder;

	private static final String CSV_PRESSURE = "/csvPressure";

	public CSVPressureController(CSVResponder pressureResponder) {
		this.pressureResponder =pressureResponder;
	}

	// Specify location, from date, and to date
	@GetMapping(CSV_PRESSURE)
	public void pressure(@RequestParam(value = Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {
		pressureResponder.handleResponse(response, location, fromDate, toDate);

	}

}
