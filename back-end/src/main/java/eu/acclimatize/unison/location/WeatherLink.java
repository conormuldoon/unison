package eu.acclimatize.unison.location;

import java.net.URI;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

/**
 * An enumeration for HATEOAS weather links.
 *
 */
public enum WeatherLink {

	CLOUDINESS(Constant.CLOUDINESS, MappingConstant.LOCATION_CLOUDINESS),
	CLOUD_LEVEL(Constant.CLOUD_LEVEL, MappingConstant.LOCATION_CLOUD_LEVEL),
	DEW_POINT(Constant.DEW_POINT, MappingConstant.LOCATION_DEW_POINT),
	GLOBAL_RADIATION(Constant.GLOBAL_RADIATION, MappingConstant.LOCATION_GLOBAL_RADIATION),
	FOG(Constant.FOG, MappingConstant.LOCATION_FOG), HUMIDITY(Constant.HUMIDITY, MappingConstant.LOCATION_HUMIDITY),
	PRECIPITATION(Constant.PRECIPITATION, MappingConstant.LOCATION_PRECIPITATION),
	PRESSURE(Constant.PRESSURE, MappingConstant.LOCATION_PRESSURE),
	TEMPERATURE(Constant.TEMPERATURE, MappingConstant.LOCATION_TEMPERATURE),
	WIND_DIRECTION(Constant.WIND_DIRECTION, MappingConstant.LOCATION_WIND_DIRECTION),
	WIND_SPEED(Constant.WIND_SPEED, MappingConstant.LOCATION_WIND_SPEED);

	private static final String REQUEST_PARAM = "{?" + Constant.FROM_DATE + "," + Constant.TO_DATE + "}";

	private String propertyName;
	private String propertyMapping;

	private WeatherLink(String propertyName, String propertyMapping) {
		this.propertyName = propertyName;
		this.propertyMapping = propertyMapping;
	}

	/**
	 * Creates a link using the current mapping using the specified location name.
	 * 
	 * @param name The location name.
	 * @return The link created.
	 */
	public Link createLink(String name, String baseURI) {

		UriTemplate uriTemplate = UriTemplate.of(baseURI + propertyMapping);
		URI uri = uriTemplate.expand(name);

		return Link.of(uri.toString() + REQUEST_PARAM, propertyName);
	}

}
