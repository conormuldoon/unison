package eu.acclimatize.unison;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * A controller class for the root or index mappings.
 */
@RestController
public class IndexHALController {

	private RootURIBuilder builder;

	public IndexHALController(RootURIBuilder builder) {
		this.builder = builder;
	}

	private void addLink(String baseURI, String mapping, String rel, List<Link> list) {
		list.add(Link.of(baseURI + mapping, rel));
	}

	/**
	 * Creates a HAL representation for the root.
	 * 
	 * @return A representational model for Unison.
	 */
	@GetMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel createModel(HttpServletResponse response) {
		response.setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
		List<Link> list = new ArrayList<>();

		String baseURI = builder.build();
		list.add(Link.of(baseURI).withSelfRel());
		list.add(Link.of(baseURI + MappingConstant.INDEX).withSelfRel());

		addLink(baseURI, MappingConstant.LOCATION_COLLECTION, Constant.LOCATION_COLLECTION, list);
		addLink(baseURI, MappingConstant.USER, Constant.USER, list);
		addLink(baseURI, MappingConstant.EXPLORER, Constant.EXPLORER, list);

		return new UnisonModel(list);
	}

	/**
	 * Creates a HAL representation for the index.
	 * 
	 * @return A representational model for Unison.
	 */
	@GetMapping(value = MappingConstant.INDEX, produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel indexHAL(HttpServletResponse response) {
		return createModel(response);
	}

	/**
	 * Maps root to the index HTML file.
	 * 
	 * @return A model and view of the index file.
	 */
	@GetMapping("/")
	public ModelAndView rootHTML(HttpServletResponse response) {

		return indexHTML(response);

	}

	/**
	 * Maps the /index endpoint to the root index HTML file.
	 * 
	 * @return A model and view of the index file.
	 */
	@GetMapping(MappingConstant.INDEX)
	public ModelAndView indexHTML(HttpServletResponse response) {
		response.setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
		return new ModelAndView(Constant.INDEX_FILE);

	}

}
