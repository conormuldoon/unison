package eu.acclimatize.unison;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import eu.acclimatize.unison.location.HALLocationController;

@RestController
public class IndexHALController {

	private static final String EXTENSION = ".html";

	@GetMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel createModel() {

		List<Link> list = new ArrayList<>();

		list.add(linkTo(methodOn(IndexHALController.class).createModel()).withSelfRel());
		list.add(linkTo(methodOn(IndexHALController.class).indexHAL()).withSelfRel());

		list.add(linkTo(methodOn(HALLocationController.class).createModel()).withRel(Constant.LOCATION_COLLECTION));

		return new UnisonModel(list);
	}

	@GetMapping(value = MappingConstant.INDEX, produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel indexHAL() {
		return createModel();
	}

	@GetMapping("/")
	public ModelAndView rootHTML() {

		return new ModelAndView(MappingConstant.INDEX + EXTENSION);

	}

	@GetMapping(MappingConstant.INDEX)
	public ModelAndView indexHTML() {

		return new ModelAndView(MappingConstant.INDEX + EXTENSION);

	}

}
