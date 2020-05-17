package eu.acclimatize.unison.location;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

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
	public WeatherProperty[] weatherProperty(@Value("${api.for}") Boolean fogSupported) {

		if (fogSupported) {
			return WeatherProperty.values();
		} else {
			// The Met Éireann HARMONIE-AROME API does not support fog.
			WeatherProperty[] weatherProperty = { WeatherProperty.CLOUDINESS, WeatherProperty.CLOUD_LEVEL,
					WeatherProperty.DEW_POINT, WeatherProperty.HUMIDITY, WeatherProperty.PRECIPITATION,
					WeatherProperty.PRESSURE, WeatherProperty.TEMPERATURE, WeatherProperty.WIND_DIRECTION,
					WeatherProperty.WIND_SPEED };
			return weatherProperty;
		}

	}

}
