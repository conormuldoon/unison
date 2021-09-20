package eu.acclimatize.unison;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 *
 */
public class RootURIBuilder {

	/**
	 * Builds the URI using the current mapping.
	 * 
	 * @return
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
