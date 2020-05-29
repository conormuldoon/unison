package eu.acclimatize.unison.location.harvester;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class HarvestModel extends RepresentationModel<HarvestModel> {

	public HarvestModel(Link link) {
		super(link);
	}
}
