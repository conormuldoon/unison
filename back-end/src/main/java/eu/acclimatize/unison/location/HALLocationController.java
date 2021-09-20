package eu.acclimatize.unison.location;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.RootURIBuilder;

/**
 * 
 * A controller to obtain an ordered list of location names and coordinates
 * sorted by location name and represented in a HAL format.
 *
 */
@RestController
public class HALLocationController {

	private LocationService locationService;
	private WeatherLink[] weatherLink;
	private RootURIBuilder builder;

	/**
	 * Creates and instance of HALLocationController.
	 * 
	 * @param locationService The service used to obtain the list of locations.
	 * @param weatherLink     The HAL weather links that are used in the
	 *                        representational model.
	 */
	public HALLocationController(LocationService locationService, WeatherLink[] weatherLink, RootURIBuilder builder) {
		this.locationService = locationService;
		this.weatherLink = weatherLink;
		this.builder = builder;
	}

	/**
	 * Obtains a representational model of a sorted list of all locations in the
	 * spatial database.
	 * 
	 * @return A representational model of stored locations.
	 */
	@GetMapping(value = MappingConstant.LOCATION_COLLECTION, produces = MediaTypes.HAL_JSON_VALUE)
	public CollectionModel<LocationModel> createModel(HttpServletResponse response) {
		response.setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
		Iterable<Location> locationList = locationService.findAllSorted();

		String baseUri = builder.build();

		Collection<LocationModel> locationCollection = new ArrayList<>();
		for (Location l : locationList) {
			locationCollection.add(l.createModel(weatherLink, baseUri));
		}

		Link link = Link.of(baseUri + MappingConstant.LOCATION_COLLECTION).withSelfRel();

		Link containsLink = Link.of(baseUri + MappingConstant.CONTAINS + "{?" + Constant.LOCATION_NAME + "}",
				Constant.CONTAINS);

		return CollectionModel.of(locationCollection, link, containsLink);

	}

	/**
	 * Obtains the location model for the specified location name.
	 * 
	 * @param locationName The name of the location.
	 * @return The HAL representational model.
	 */
	@GetMapping(value = MappingConstant.SPECIFIC_LOCATION, produces = MediaTypes.HAL_JSON_VALUE)
	public LocationModel location(HttpServletResponse response,
			@PathVariable(Constant.LOCATION_NAME) String locationName) {

		response.setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);

		Location location = locationService.find(locationName);

		String baseURI = builder.build();
		return location.createModel(weatherLink, baseURI);
	}

}
