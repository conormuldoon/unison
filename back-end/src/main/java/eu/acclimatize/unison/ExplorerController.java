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
	 * Maps the /explorer endpoint to the explorer HTML file.
	 * 
	 * @return A model and view for the explorer file.
	 */
	@GetMapping(MappingConstant.EXPLORER)
	public ModelAndView explorer(HttpServletResponse response) {

		return new ModelAndView(MappingConstant.EXPLORER + ".html");

	}
}
