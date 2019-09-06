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
public class FogController {

	ItemListFinder fogFinder;
	private static final String FOG = "/fog";

	public FogController(ItemListFinder fogFinder) {
		this.fogFinder=fogFinder;
	}

	@GetMapping(FOG)
	public Iterable<HarmonieItem> fog(@RequestParam(Constant.LOCATION) String location,
			@RequestParam(value = Constant.FROM_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date fromDate,
			@RequestParam(value = Constant.TO_DATE) @DateTimeFormat(pattern = Constant.FORMAT) Date toDate) {

		return fogFinder.find(location, fromDate, toDate);
	}

}
