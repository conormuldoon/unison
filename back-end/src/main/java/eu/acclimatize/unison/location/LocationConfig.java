package eu.acclimatize.unison.location;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import eu.acclimatize.unison.WeatherLink;

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

	/**
	 * Links used for HAL representational models of locations and the location
	 * collection.
	 * 
	 * * @param fogSupported Determines whether the fog link should be included in
	 * the returned array.
	 * 
	 * @return The weather links to use in the models.
	 * 
	 */
	@Bean
	public WeatherLink[] weatherLink(@Value("${harmonie.fog}") Boolean fogSupported,
			@Value("${harmonie.globalRadiation}") Boolean grSupported) {

		if (fogSupported && grSupported) {
			return WeatherLink.values();
		} else if (fogSupported) {
			// The Met Éireann HARMONIE-AROME endpoint does not support fog.
			WeatherLink[] weatherLink = { WeatherLink.CLOUDINESS, WeatherLink.CLOUD_LEVEL, WeatherLink.DEW_POINT,
					WeatherLink.FOG, WeatherLink.HUMIDITY, WeatherLink.PRECIPITATION, WeatherLink.PRESSURE,
					WeatherLink.TEMPERATURE, WeatherLink.WIND_DIRECTION, WeatherLink.WIND_SPEED };

			return weatherLink;
		} else if (grSupported) {
			// The Norwegian Meteorological Institute HARMONIE-AROME endpoint does not
			// support global radiation.
			WeatherLink[] weatherLink = { WeatherLink.CLOUDINESS, WeatherLink.CLOUD_LEVEL, WeatherLink.DEW_POINT,
					WeatherLink.GLOBAL_RADIATION, WeatherLink.HUMIDITY, WeatherLink.PRECIPITATION, WeatherLink.PRESSURE,
					WeatherLink.TEMPERATURE, WeatherLink.WIND_DIRECTION, WeatherLink.WIND_SPEED };

			return weatherLink;
		} else {
			WeatherLink[] weatherLink = { WeatherLink.CLOUDINESS, WeatherLink.CLOUD_LEVEL, WeatherLink.DEW_POINT,
					WeatherLink.HUMIDITY, WeatherLink.PRECIPITATION, WeatherLink.PRESSURE, WeatherLink.TEMPERATURE,
					WeatherLink.WIND_DIRECTION, WeatherLink.WIND_SPEED };
			return weatherLink;

		}

	}

}
