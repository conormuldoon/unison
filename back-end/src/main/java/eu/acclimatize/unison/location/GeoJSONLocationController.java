package eu.acclimatize.unison.location;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

/**
 * 
 * A controller to obtain an ordered list of location names and coordinates
 * sorted by location name and formatted as a GeoJSON feature collection.
 *
 */
@RestController
public class GeoJSONLocationController {

	private static final String GEOJSON_MEDIA_TYPE = "application/geo+json";

	private LocationRepository locationRepository;
	private Sort sort;

	/**
	 * Creates and instance of GeoJSONLocationController.
	 * 
	 * @param locationRepository The repository where the location data is stored.
	 * @param sort               Determines that order of the features in the
	 *                           feature collection.
	 */
	public GeoJSONLocationController(LocationRepository locationRepository, Sort sort) {
		this.locationRepository = locationRepository;
		this.sort = sort;
	}

	/**
	 * Obtains a feature collection where the features represent a sorted list of
	 * all coordinates in the spatial database.
	 * 
	 * @return A list of Jackson annotated coordinates.
	 */
	@GetMapping(value = MappingConstant.LOCATION, produces = GEOJSON_MEDIA_TYPE)
	public FeatureCollection location() {

		return new FeatureCollection(locationRepository.findAll(sort));

	}

	/**
	 * Obtains the location for the specified location name.
	 * 
	 * @param locationName The name of the location.
	 * @return The location obtained from the repository.
	 */
	@GetMapping(value = MappingConstant.LOCATION + "/{" + Constant.LOCATION_NAME + "}", produces = GEOJSON_MEDIA_TYPE)
	public Location location(@PathVariable(Constant.LOCATION_NAME) String locationName) {

		Location location = locationRepository.findById(locationName)
				.orElseThrow(() -> new LocationNotFoundException(locationName));

		return location;
	}

}
