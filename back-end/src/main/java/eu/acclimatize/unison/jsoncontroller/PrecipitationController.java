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
public class PrecipitationController {

	ItemListFinder precipitationFinder;
	private static final String PRECIPITATION = "/precipitation";

	public PrecipitationController(ItemListFinder precipitationFinder) {
		this.precipitationFinder = precipitationFinder;
	}

	@GetMapping(PRECIPITATION)
	public Iterable<HarmonieItem> precipitation(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return precipitationFinder.find(location, fromDate, toDate);

	}

}
