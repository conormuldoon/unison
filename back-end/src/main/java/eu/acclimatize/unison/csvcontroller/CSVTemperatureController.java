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
public class CSVTemperatureController {

	CSVResponder temperatureResponder;

	private static final String CSV_TEMP = "/csvTemperature";

	public CSVTemperatureController(CSVResponder temperatureResponder) {
		this.temperatureResponder = temperatureResponder;
	}

	// Specify location, from date, and to date
	@GetMapping(CSV_TEMP)
	public void temperature(@RequestParam(value = Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {
		temperatureResponder.handleResponse(response, location, fromDate, toDate);

	}

}
