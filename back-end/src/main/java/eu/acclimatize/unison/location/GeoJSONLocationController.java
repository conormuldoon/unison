package eu.acclimatize.unison.location;

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

	private LocationService locationService;

	/**
	 * Creates and instance of GeoJSONLocationController.
	 * 
	 * @param locationRepository The repository where the location data is stored.
	 * @param sort               Determines that order of the features in the
	 *                           feature collection.
	 */
	public GeoJSONLocationController(LocationService locationService) {
		this.locationService = locationService;

	}

	/**
	 * Obtains a feature collection where the features represent a sorted list of
	 * all coordinates in the spatial database.
	 * 
	 * @return A list of GeoJSON feature collection of point features for the added
	 *         locations.
	 */
	@GetMapping(value = MappingConstant.LOCATION_COLLECTION, produces = LocationConstant.GEOJSON_MEDIA_TYPE)
	public FeatureCollection location() {

		return new FeatureCollection(locationService.findAllSorted());

	}

	/**
	 * Obtains the location for the specified location name.
	 * 
	 * @param locationName The name of the location.
	 * @return The location obtained from the repository.
	 */
	@GetMapping(value = MappingConstant.SPECIFIC_LOCATION, produces = LocationConstant.GEOJSON_MEDIA_TYPE)
	public Location location(@PathVariable(Constant.LOCATION_NAME) String locationName) {

		return locationService.find(locationName);

	}

}
