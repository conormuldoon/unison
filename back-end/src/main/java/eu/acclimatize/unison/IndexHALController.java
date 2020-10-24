package eu.acclimatize.unison;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import eu.acclimatize.unison.location.HALLocationController;

/** 
 * 
 * A controller class for the root or index mappings. 
 */
@RestController
public class IndexHALController {

	private static final String EXTENSION = ".html";

	/**
	 * Creates a HAL representation for the root.
	 * 
	 * @return A representational model for Unison.
	 */
	@GetMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel createModel() {

		List<Link> list = new ArrayList<>();

		list.add(linkTo(methodOn(IndexHALController.class).createModel()).withSelfRel());
		list.add(linkTo(methodOn(IndexHALController.class).indexHAL()).withSelfRel());

		list.add(linkTo(methodOn(HALLocationController.class).createModel()).withRel(Constant.LOCATION_COLLECTION));

		String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
		list.add(Link.of(baseUri + MappingConstant.USER, Constant.USER));

		return new UnisonModel(list);
	}

	/**
	 * Creates a HAL representation for the index.
	 * 
	 * @return A representational model for Unison.
	 */
	@GetMapping(value = MappingConstant.INDEX, produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel indexHAL() {
		return createModel();
	}

	/**
	 * Maps root to the index HTML file.
	 * 
	 * @return A model and view of the index file.
	 */
	@GetMapping("/")
	public ModelAndView rootHTML() {

		return indexHTML();

	}

	/**
	 * Map index to the index HTML file. 
	 * 
	 * @return A model and view of the index file.
	 */
	@GetMapping(MappingConstant.INDEX)
	public ModelAndView indexHTML() {

		return new ModelAndView(MappingConstant.INDEX + EXTENSION);

	}

}
