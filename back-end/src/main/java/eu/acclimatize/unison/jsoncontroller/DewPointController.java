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
public class DewPointController {

	ItemListFinder dewPointFinder;
	private static final String MAPPING = "/dewPoint";

	public DewPointController(ItemListFinder dewPointFinder) {
		this.dewPointFinder = dewPointFinder;
	}

	@GetMapping(MAPPING)
	public Iterable<HarmonieItem> dewPoint(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return dewPointFinder.find(location, fromDate, toDate);
	}

}
