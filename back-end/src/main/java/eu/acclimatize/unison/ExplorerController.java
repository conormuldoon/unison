package eu.acclimatize.unison;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * A controller for the API explorer.
 */
@Controller
public class ExplorerController {

	/**
	 * Maps the /explorer end-point to the explorer HTML file.
	 * 
	 * @param response The HTTP servlet response used to add the vary header.
	 * @return A model and view for the explorer file.
	 */
	@GetMapping(MappingConstant.EXPLORER)
	public ModelAndView explorer(HttpServletResponse response) {

		return new ModelAndView(MappingConstant.EXPLORER + ".html");

	}
}
