package eu.acclimatize.unison.location;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
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
public class HATEOASLocationController {

	private LocationService locationService;
	private WeatherLink[] weatherLink;

	/**
	 * Creates and instance of GeoJSONLocationController.
	 * 
	 * @param locationRepository The repository where the location data is stored.
	 * @param sort               Determines that order of the features in the
	 *                           feature collection.
	 */
	public HATEOASLocationController(LocationService locationService, WeatherLink[] weatherLink) {
		this.locationService = locationService;
		this.weatherLink = weatherLink;
	}

	private void addLink(String template, String rel, List<Link> list) {
		String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
		UriTemplate uriTemplate = UriTemplate.of(baseUri + template);
		uriTemplate = uriTemplate.with(Constant.LOCATION_NAME, TemplateVariable.VariableType.PATH_VARIABLE);
		Link link = Link.of(uriTemplate, rel);
		list.add(link);

	}

	/**
	 * Obtains a feature collection where the features represent a sorted list of
	 * all coordinates in the spatial database.
	 * 
	 * @return A list of Jackson annotated coordinates.
	 */
	@GetMapping(value = MappingConstant.LOCATION_COLLECTION, produces = MediaTypes.HAL_JSON_VALUE)
	public FCModel location() {

		FeatureCollection featureCollection = new FeatureCollection(locationService.findAllSorted());

		List<Link> list = new ArrayList<>();

		list.add(linkTo(methodOn(HATEOASLocationController.class).location()).withSelfRel());

		addLink(MappingConstant.SPECIFIC_LOCATION, "location", list);

		for (WeatherLink wl : weatherLink) {
			list.add(wl.createLink());
		}

		addLink(MappingConstant.HARVEST, Constant.HARVEST, list);
		FCModel model = new FCModel(list, featureCollection);
		return model;

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

		List<Link> list = location.createList(weatherLink);
		LocationModel model = new LocationModel(list, location);

		return model;
	}

}
