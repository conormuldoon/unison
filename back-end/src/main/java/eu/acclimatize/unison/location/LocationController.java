package eu.acclimatize.unison.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * A controller to obtain an ordered list of location names and coordinates
 * sorted by location name and formatted as a GeoJSON feature collection.
 *
 */
@RestController
public class LocationController {

	private static final String LOCATION = "/location";
	private static final String MEDIA_TYPE = "application/geo+json";

	private CoordinatesStore store;
	private PointSerializer pointSerializer;

	/**
	 * Creates and instance of LocationController.
	 * 
	 * @param store           Uses spatial database functionality to obtain a list
	 *                        of coordinates along with location names.
	 * 
	 * @param pointSerializer Used in serializing points.
	 */
	public LocationController(CoordinatesStore store, PointSerializer pointSerializer) {
		this.store = store;
		this.pointSerializer = pointSerializer;
	}

	/**
	 * Obtains a feature collection where the features represent a sorted list of
	 * all coordinates in the spatial database.
	 * 
	 * @return A list of Jackson annotated coordinates.
	 */
	@GetMapping(value = LOCATION, produces = MEDIA_TYPE)
	public FeatureCollection location() {

		System.out.println("Called");
		return new FeatureCollection(store.sortedFindAll(), pointSerializer);

	}
}
