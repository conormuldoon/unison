package eu.acclimatize.unison.location;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationModel extends RepresentationModel<LocationModel> {

	@JsonProperty
	private Location content;

	public LocationModel(List<Link> list, Location content) {
		super(list);
		this.content = content;
	}

}
