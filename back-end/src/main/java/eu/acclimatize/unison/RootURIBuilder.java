package eu.acclimatize.unison;

import javax.servlet.http.HttpServletRequest;

/**
 * A builder for creating base URIs that uses forwarded headers of 
 * requests if present. The base URIs are used in creating hrefs
 * for HATEOAS/HAL. 
 *
 */
public class RootURIBuilder {


	/**
	 * Builds URIs using the current request for HATEOAS. If forwarding
	 * headers are present in the request the scheme, server name, and
	 * port will be updated due to a forwarded header filter bean 
	 * initialised in {@link UnisonServerApplication}. The build URIs 
	 * include the context path if present.
	 * 
	 * @param request The current request.
	 * @return The built URI.
	 */
	public String build(HttpServletRequest request) {
		String scheme = request.getScheme();
		StringBuilder sb=new StringBuilder(scheme);
		sb.append("://");
		sb.append(request.getServerName());
		int port = request.getServerPort();
		if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
			sb.append(":");
			sb.append(port);
		}
		
		sb.append(request.getContextPath());
		
		return sb.toString();
	}

}
