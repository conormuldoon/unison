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
public class HumidityController {

	ItemListFinder humidityFinder;
	private static final String HUMIDITY = "/humidity";

	public HumidityController(ItemListFinder humidityFinder) {
		this.humidityFinder = humidityFinder;
	}

	@GetMapping(HUMIDITY)
	public Iterable<HarmonieItem> humidity(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return humidityFinder.find(location, fromDate, toDate);
	}

}
