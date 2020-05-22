package eu.acclimatize.unison;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class UnisonModel extends RepresentationModel<UnisonModel>{
	
	
	public UnisonModel(List<Link> list) {
		super(list);
	}

}
