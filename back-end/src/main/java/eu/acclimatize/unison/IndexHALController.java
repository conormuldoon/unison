package eu.acclimatize.unison;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import eu.acclimatize.unison.location.HATEOASLocationController;

@RestController
public class IndexHALController {

	@GetMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel createModel() {

		List<Link> list = new ArrayList<>();
		list.add(linkTo(methodOn(HATEOASLocationController.class).location()).withRel(Constant.LOCATION_COLLECTION));
		list.add(linkTo(IndexHALController.class).withSelfRel());
		list.add(linkTo(methodOn(IndexHALController.class).index()).withSelfRel());
		UnisonModel model = new UnisonModel(list);
		return model;
	}

	@GetMapping(value = "/index", produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel index() {
		return createModel();
	}

	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView indexHTML() {

		return new ModelAndView("forward:/index.html");

	}

}
