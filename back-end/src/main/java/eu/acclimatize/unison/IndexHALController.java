package eu.acclimatize.unison;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

	private BaseURIBuilder builder;

	/**
	 * Creates an instance of IndexHALController.
	 * 
	 * @param builder Used in creating links.
	 */
	public IndexHALController(BaseURIBuilder builder) {
		this.builder = builder;
	}

	private void addLink(String baseURI, String mapping, String rel, List<Link> list) {
		list.add(Link.of(baseURI + mapping, rel));
	}

	/**
	 * Creates a HAL representation for the root.
	 * 
	 * @param response The HTTP servlet response used to add the HTTP vary header.
	 * @param request  The HTTP servlet request used in creating base URIs.
	 * @return A representational model for Unison.
	 */
	@GetMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel createModel(HttpServletResponse response, HttpServletRequest request) {
		response.setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
		List<Link> list = new ArrayList<>();

		String baseURI = builder.build(request.getScheme(), request.getServerName(), request.getServerPort(),
				request.getContextPath());
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
	 * @param response The HTTP servlet response used to add the HTTP vary header.
	 * @param request  The HTTP servlet request used in creating base URIs.
	 * @return A representational model for Unison.
	 */
	@GetMapping(value = MappingConstant.INDEX, produces = MediaTypes.HAL_JSON_VALUE)
	public UnisonModel indexHAL(HttpServletResponse response, HttpServletRequest request) {
		return createModel(response, request);
	}

	/**
	 * Maps root to the index HTML file.
	 * 
	 * @param response The HTTP servlet response used to add the HTTP vary header.
	 * @return A model and view of the index file.
	 */
	@GetMapping("/")
	public ModelAndView rootHTML(HttpServletResponse response) {

		return indexHTML(response);

	}

	/**
	 * Maps the /index end-point to the root index HTML file.
	 * 
	 * @param response The HTTP servlet response used to add the HTTP vary header.
	 * @return A model and view of the index file.
	 */
	@GetMapping(MappingConstant.INDEX)
	public ModelAndView indexHTML(HttpServletResponse response) {
		response.setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
		return new ModelAndView(Constant.INDEX_FILE);

	}

}
