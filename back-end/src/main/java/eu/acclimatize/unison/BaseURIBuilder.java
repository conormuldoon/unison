package eu.acclimatize.unison;

/**
 * Builds URIs for use with HATEOAS/HAL and the location header when new
 * resources are created. A forwarded filter bean created in
 * {@link UnisonServerApplication} ensures that the schema, server name, and
 * port reflect the correct values if requests are forwarded via a proxy.
 *
 */
public class BaseURIBuilder {

	/**
	 * Creates a base URI.
	 * 
	 * @param scheme      The scheme of local server or forwarding proxy.
	 * @param serverName  The name of local server or forwarding proxy.
	 * @param port        The port of local server or forwarding proxy.
	 * @param contextPath The context path, which precedes the servlet path. The
	 *                    context path may be an empty string.
	 * @return The built URI.
	 */
	public String build(String scheme, String serverName, int port, String contextPath) {

		StringBuilder sb = new StringBuilder(scheme);
		sb.append("://");
		sb.append(serverName);

		if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
			sb.append(":");
			sb.append(port);
		}

		sb.append(contextPath);

		return sb.toString();
	}

}
