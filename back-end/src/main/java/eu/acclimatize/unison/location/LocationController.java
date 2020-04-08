package eu.acclimatize.unison.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * A controller to obtain an ordered list of location names and coordinates
 * sorted by location name.
 *
 */
@RestController
public class LocationController {

	private CoordinatesStore store;
	private static final String LOCATION = "/location";
	private PointSerializer pointSerializer;

	/**
	 * Creates and instance of LocationController.
	 * 
	 * @param store Uses spatial database functionality to obtain a list of
	 *              coordinates along with location names.
	 */
	public LocationController(CoordinatesStore store, PointSerializer pointSerializer) {
		this.store = store;
		this.pointSerializer = pointSerializer;
	}

	/**
	 * Obtains a sorted list of all coordinates in the spatial database.
	 * 
	 * @return A list of Jackson annotated coordinates.
	 */
	@GetMapping(LOCATION)
	public FeatureCollection location() {

		return new FeatureCollection(store.sortedFindAll(),pointSerializer);

	}
}
