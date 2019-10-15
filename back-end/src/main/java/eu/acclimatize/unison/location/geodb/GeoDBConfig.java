package eu.acclimatize.unison.location.geodb;

import org.locationtech.jts.io.WKTReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * A configuration class for GeoDB. Similar to {@link eu.acclimatize.unison.location.postgis.PostGISConfig}, 
 * but has dependencies on the org.locationtech package.
 *
 */
@Configuration
public class GeoDBConfig {

	/**
	 * Creates a reader that converts a geometry in Well-Known Text (WKT) format
	 * to a point object for storage in GeoDB.
	 * 
	 * @return The reader created.
	 */
	@Bean
	public WKTReader wktReader(){
		return new WKTReader();
	}
}
