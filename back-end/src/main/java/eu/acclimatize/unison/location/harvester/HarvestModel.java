package eu.acclimatize.unison.location.harvester;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;


/**
 * A representational model for harvesting. 
 *
 */
public class HarvestModel extends RepresentationModel<HarvestModel> {

	/**
	 * Creates an instance of HarvestModel.
	 * 
	 * @param link The link for harvesting.
	 */
	public HarvestModel(Link link) {
		super(link);
	}
}
