package eu.acclimatize.unison;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

/**
 * A representational model for Unison.
 *
 */
public class UnisonModel extends RepresentationModel<UnisonModel>{
	
	/**
	 * Creates an instance of UnisonModel.
	 * 
	 * @param list The links associated with the Unison root or index.
	 */
	public UnisonModel(List<Link> list) {
		super(list);
	}

}
