package eu.acclimatize.unison.location;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FCModel extends RepresentationModel<FCModel> {

	@JsonProperty
	private FeatureCollection content;

	public FCModel(List<Link> list, FeatureCollection content) {
		super(list);
		this.content = content;
	}

}
