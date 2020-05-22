package eu.acclimatize.unison;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.location.HATEOASLocationController;

@RestController
public class HALController {

	@GetMapping(value = "/hal")
	public UnisonModel unisonModel() {

		List<Link> list = new ArrayList<>();
		list.add(linkTo(methodOn(HATEOASLocationController.class).location()).withRel(Constant.LOCATION_COLLECTION));
		list.add(linkTo(methodOn(HALController.class).unisonModel()).withSelfRel());
		UnisonModel model = new UnisonModel(list);
		return model;
	}

}
