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
public class TemperatureController {

	ItemListFinder temperatureFinder;
	private static final String TEMP = "/temperature";

	public TemperatureController(ItemListFinder temperatureFinder) {
		this.temperatureFinder = temperatureFinder;
	}

	@GetMapping(TEMP)
	public Iterable<HarmonieItem> temperature(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return temperatureFinder.find(location, fromDate, toDate);
	}

}
