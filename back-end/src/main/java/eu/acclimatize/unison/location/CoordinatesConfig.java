package eu.acclimatize.unison.location;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

/**
 * 
 * A configuration class for {@link CoordinatesStore}s.
 *
 */
@Configuration
public class CoordinatesConfig {

	/**
	 * A bean used to determine how coordinates queries are sorted.
	 * 
	 * @return The sort option. Queries are sorted in ascending order base on the location name column.
	 */
	@Bean
	public Sort sort() {
		
		return Sort.by(Sort.Direction.ASC, "name");
		
	}

	/**
	 * A bean used in serializing location data in a GeoJSON format.
	 * 
	 * @return The point serializer created.
	 */
	@Bean
	public PointFeatureSerializer pointSerializer() {
		return new PointFeatureSerializer();
	}
}
