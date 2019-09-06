package eu.acclimatize.unison.jsoncontroller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.ItemListFinder;

@RestController
public class WindSpeedController {

	ItemListFinder windSpeedFinder;
	private static final String MAPPING = "/windSpeed";

	public WindSpeedController(ItemListFinder windSpeedFinder) {
		this.windSpeedFinder = windSpeedFinder;
	}

	@GetMapping(MAPPING)
	public Iterable<HarmonieItem> windSpeed(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return windSpeedFinder.find(location, fromDate, toDate);
	}

}
