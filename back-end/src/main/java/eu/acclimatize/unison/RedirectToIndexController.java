package eu.acclimatize.unison;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * A controller for redirecting the /unison mapping to the /unison/index.html file.
 * The controller is used when the user does not specify the index file and the React
 * components are packaged with the server code for a binary release, rather than
 * being served from a separate server, such as Nginx.
 *
 */
@RestController
public class RedirectToIndexController {

	private static final String UNISON = "/unison";
	private static final String INDEX_PATH_NAME = UNISON + "/index.html";

	/**
	 * Redirects the /unison request to the index.html file for the single-page application.
	 * 
	 * @param response Used to send the redirect.
	 * @throws IOException Thrown if there is an I/O problem when sending the redirect.
	 */
	@GetMapping(UNISON)
	public void redirectToIndex(HttpServletResponse response) throws IOException {
		response.sendRedirect(INDEX_PATH_NAME);
	}

}
