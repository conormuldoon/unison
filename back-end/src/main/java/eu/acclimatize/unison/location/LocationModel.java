package eu.acclimatize.unison.location;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A representational model class for locations.
 *
 */
public class LocationModel extends RepresentationModel<LocationModel> {

	@JsonProperty
	private String name;

	/**
	 * Creates an instance of LocationModel.
	 * 
	 * @param list The list of links to include the model.
	 * @param name The name of the location.
	 */
	public LocationModel(List<Link> list, String name) {
		super(list);
		this.name = name;
	}

}
