package eu.acclimatize.unison;

public class BaseURIBuilder {

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
