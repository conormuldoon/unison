package eu.acclimatize.unison;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * 
 * A controller that forwards / and /unison mappings to the /unison/index.html file.
 * The controller is used when the React
 * components are packaged with the server code for a binary release.
 *
 */
@Controller
public class ForwardToIndexController {

	private static final String UNISON = "/unison";
	private static final String ROOT = "/";

	/**
	 * Used to forward / and /unison requests to the /unison/index.html file for the React application, which
	 * is included in the public folder for a binary release.
	 * 
	 * @return The page to forward to.
	 */
	@GetMapping(value= {ROOT, UNISON})
	public String forwardToIndex(){
		return "forward:/unison/index.html";
	}

}
