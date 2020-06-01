package eu.acclimatize.unison.location;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.WeatherLink;

/**
 * 
 * A controller to obtain an ordered list of location names and coordinates
 * sorted by location name and formatted as a GeoJSON feature collection.
 *
 */
@RestController
public class HALLocationController {

	private LocationService locationService;
	private WeatherLink[] weatherLink;

	/**
	 * Creates and instance of GeoJSONLocationController.
	 * 
	 * @param locationRepository The repository where the location data is stored.
	 * @param sort               Determines that order of the features in the
	 *                           feature collection.
	 */
	public HALLocationController(LocationService locationService, WeatherLink[] weatherLink) {
		this.locationService = locationService;
		this.weatherLink = weatherLink;
	}

	/**
	 * Obtains a feature collection where the features represent a sorted list of
	 * all coordinates in the spatial database.
	 * 
	 * @return A list of Jackson annotated coordinates.
	 */
	@GetMapping(value = MappingConstant.LOCATION_COLLECTION, produces = MediaTypes.HAL_JSON_VALUE)
	public CollectionModel<LocationModel> createModel() {

		Iterable<Location> locationList = locationService.findAllSorted();

		Collection<LocationModel> locationCollection = new ArrayList<>();
		for (Location l : locationList) {
			locationCollection.add(l.createModel(weatherLink));
		}

		Link link = linkTo(methodOn(HALLocationController.class).createModel()).withSelfRel();
		String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
		Link containsLink = Link.of(baseUri + MappingConstant.CONTAINS + "{?" + Constant.LOCATION_NAME + "}",
				Constant.CONTAINS);

		return CollectionModel.of(locationCollection, link, containsLink);

	}

	/**
	 * Obtains the location for the specified location name.
	 * 
	 * @param locationName The name of the location.
	 * @return The location obtained from the repository.
	 */
	@GetMapping(value = MappingConstant.SPECIFIC_LOCATION, produces = MediaTypes.HAL_JSON_VALUE)
	public LocationModel location(@PathVariable(Constant.LOCATION_NAME) String locationName) {

		Location location = locationService.find(locationName);

		return location.createModel(weatherLink);
	}

}
