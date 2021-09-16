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

		WeatherLink[] values = WeatherLink.values();
		int n = values.length;

		// The Met Éireann HARMONIE-AROME endpoint does not support fog.
		if (!fogSupported) {
			n--;
		}

		// The Norwegian Meteorological Institute HARMONIE-AROME endpoint does not
		// support global radiation.
		if (!grSupported) {
			n--;
		}
		
		WeatherLink[] wl = new WeatherLink[n];
		int curI = 0;
		for (WeatherLink v : values) {
			if (v == WeatherLink.FOG) {
				if (fogSupported) {
					wl[curI++] = v;
				}
			} else if (v == WeatherLink.GLOBAL_RADIATION) {
				if (grSupported) {
					wl[curI++] = v;
				}
			} else {
				wl[curI++] = v;
			}
		}

		return wl;

	}

}
