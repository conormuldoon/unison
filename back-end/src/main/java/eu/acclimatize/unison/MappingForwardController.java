package eu.acclimatize.unison;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 
 * A controller that forwards requests prefixed with /apiacc/ to root with the remainder of the request path
 * intact. This is required when the React components are being served by Spring Boot (such as in
 * a binary release). If the React components are served by NginX, Apache, or the React development
 * server, the servers can be configured to rewrite the paths. 
 *
 */
@Controller
public class MappingForwardController {

	private static final String APIACC = "/apiacc/*";
	private static final String FORWARD="forward:";
	private static final int START_INDEX = 7;
	
	/**
	 * Used to forward requests prefixed with /apiacc/ to root with the remainder of the request path intact. 
	 * 
	 * @param request Used to obtain the request path
	 * @return The mapping to forward to.
	 */
	@RequestMapping(APIACC)
	public String forward(HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return FORWARD + path.substring(START_INDEX, path.length());
	}

}
