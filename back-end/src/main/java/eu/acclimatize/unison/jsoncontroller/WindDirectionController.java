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
public class WindDirectionController {

	ItemListFinder windDirectionFinder;
	private static final String MAPPING = "/windDirection";

	public WindDirectionController(ItemListFinder windDirectionFinder) {
		this.windDirectionFinder = windDirectionFinder;
	}

	@GetMapping(MAPPING)
	public Iterable<HarmonieItem> windDirection(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return windDirectionFinder.find(location, fromDate, toDate);
	}

}
