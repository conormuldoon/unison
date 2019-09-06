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
public class CSVWindSpeedController {

	CSVResponder windSpeedResponder;
	private static final String WINSPEED = "/csvWindSpeed";

	public CSVWindSpeedController(CSVResponder windSpeedResponder) {
		this.windSpeedResponder=windSpeedResponder;
	}

	@GetMapping(WINSPEED)
	public void windSpeed(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate,
			HttpServletResponse response) throws IOException {

		windSpeedResponder.handleResponse(response, location, fromDate, toDate);
	}

}
