package eu.acclimatize.unison.location.postgis;

import com.vividsolutions.jts.io.WKTReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * A configuration class for PostGIS. Similar to {@link eu.acclimatize.unison.location.geodb.GeoDBConfig}, 
 * but has dependencies on the com.vividsolutions package.
 *
 */
@Configuration
public class PostGISConfig {

	/**
	 * Creates a reader that converts a geometry in Well-Known Text (WKT) format
	 * to a point object for storage in PostGIS.
	 * 
	 * @return The reader created.
	 */
	@Bean
	public WKTReader wktReader(){
		return new WKTReader();
	}
}
