package eu.acclimatize.unison.location;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationModel extends RepresentationModel<LocationModel> {

	@JsonProperty
	private String name;

	public LocationModel(List<Link> list, String name) {
		super(list);
		this.name = name;
	}

}
