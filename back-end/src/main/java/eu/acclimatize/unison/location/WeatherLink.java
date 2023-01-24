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

	/**
	 * The weather link for the cloudiness property name and mapping.
	 */
	CLOUDINESS(Constant.CLOUDINESS, MappingConstant.LOCATION_CLOUDINESS),
	/**
	 * The weather link for the cloud level property name and mapping.
	 */
	CLOUD_LEVEL(Constant.CLOUD_LEVEL, MappingConstant.LOCATION_CLOUD_LEVEL),
	/**
	 * The weather link for the dew point property name and mapping.
	 */
	DEW_POINT(Constant.DEW_POINT, MappingConstant.LOCATION_DEW_POINT),
	/**
	 * The weather link for the global radiation property name and mapping.
	 */
	GLOBAL_RADIATION(Constant.GLOBAL_RADIATION, MappingConstant.LOCATION_GLOBAL_RADIATION),
	/**
	 * The weather link for the fog property name and mapping.
	 */
	FOG(Constant.FOG, MappingConstant.LOCATION_FOG), 
	/**
	 * The weather link for the humidity property name and mapping.
	 */
	HUMIDITY(Constant.HUMIDITY, MappingConstant.LOCATION_HUMIDITY),
	/**
	 * The weather link for the precipitation property name and mapping.
	 */
	PRECIPITATION(Constant.PRECIPITATION, MappingConstant.LOCATION_PRECIPITATION),
	/**
	 * The weather link for the pressure property name and mapping.
	 */
	PRESSURE(Constant.PRESSURE, MappingConstant.LOCATION_PRESSURE),
	/**
	 * The weather link for the temperature property name and mapping.
	 */
	TEMPERATURE(Constant.TEMPERATURE, MappingConstant.LOCATION_TEMPERATURE),
	/**
	 * The weather link for the wind direction property name and mapping.
	 */
	WIND_DIRECTION(Constant.WIND_DIRECTION, MappingConstant.LOCATION_WIND_DIRECTION),
	/**
	 * The weather link for the wind speed property name and mapping.
	 */
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
	 * @param baseURI The base URI for a template used in creating a link.
	 * 
	 * @return The link created.
	 */
	public Link createLink(String name, String baseURI) {

		UriTemplate uriTemplate = UriTemplate.of(baseURI + propertyMapping);
		URI uri = uriTemplate.expand(name);

		return Link.of(uri.toString() + REQUEST_PARAM, propertyName);
	}

}
