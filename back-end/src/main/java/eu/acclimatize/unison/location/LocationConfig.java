package eu.acclimatize.unison.location;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.web.util.UriTemplate;

import eu.acclimatize.unison.MappingConstant;

/**
 * 
 * A configuration class for the location package.
 *
 */
@Configuration
public class LocationConfig {

	/**
	 * A bean for a factory that is used to create points.
	 * 
	 * @return The geometry factory created.
	 */
	@Bean
	public GeometryFactory geometryFactory() {
		return new GeometryFactory();
	}

	/**
	 * A bean used to determine how coordinates queries are sorted.
	 * 
	 * @return The sort option. Queries are sorted in ascending order base on the
	 *         location name column.
	 */
	@Bean
	public Sort sort() {

		return Sort.by(Sort.Direction.ASC, "name");

	}

	@Bean
	/**
	 * Properties used in the GeoJSON representation of the locations.
	 */
	public WeatherProperty[] weatherProperty() {

		return WeatherProperty.values();
	}

	@Bean
	public UriTemplate harvestUnison() {
		return new UriTemplate(MappingConstant.HARVEST);
	}
}
