package eu.acclimatize.unison.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

	CoordinatesStore store;
	private static final String LOCATION = "/location";

	public LocationController(CoordinatesStore store) {
		this.store = store;
	}

	@GetMapping(LOCATION)
	Iterable<? extends Object> location() {
		return store.sortedFindAll();

	}
}
