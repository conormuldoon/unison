package eu.acclimatize.unison.location;

import org.springframework.data.domain.Sort;
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

	private LocationRepository locationRepository;
	private Sort sort;

	/**
	 * Creates and instance of LocationController.
	 * 
	 * @param locationRepository The repository where the location data is stored.
	 * @param sort Determines that order of the features in the feature collection.
	 */
	public LocationController(LocationRepository locationRepository, Sort sort) {
		this.locationRepository=locationRepository;
		this.sort=sort;
	}

	/**
	 * Obtains a feature collection where the features represent a sorted list of
	 * all coordinates in the spatial database.
	 * 
	 * @return A list of Jackson annotated coordinates.
	 */
	@GetMapping(value = LOCATION, produces = MEDIA_TYPE)
	public FeatureCollection location() {

		return new FeatureCollection(locationRepository.findAll(sort));

	}
}
