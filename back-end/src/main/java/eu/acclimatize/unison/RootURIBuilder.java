package eu.acclimatize.unison;

import org.springframework.hateoas.server.mvc.BasicLinkBuilder;

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
	public String build() {
		return BasicLinkBuilder.linkToCurrentMapping().toString();
	}

}
