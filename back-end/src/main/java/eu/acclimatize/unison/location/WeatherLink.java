package eu.acclimatize.unison.location;

import java.net.URI;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

public enum WeatherLink {

	CLOUDINESS(Constant.CLOUDINESS, MappingConstant.LOCATION_CLOUDINESS),
	CLOUD_LEVEL(Constant.CLOUD_LEVEL, MappingConstant.LOCATION_CLOUD_LEVEL),
	DEW_POINT(Constant.DEW_POINT, MappingConstant.LOCATION_DEW_POINT), FOG(Constant.FOG, MappingConstant.LOCATION_FOG),
	HUMIDITY(Constant.HUMIDITY, MappingConstant.LOCATION_HUMIDITY),
	PRECIPITATION(Constant.PRECIPITATION, MappingConstant.LOCATION_PRECIPITATION),
	PRESSURE(Constant.PRESSURE, MappingConstant.LOCATION_PRESSURE),
	TEMPERATURE(Constant.TEMPERATURE, MappingConstant.LOCATION_TEMPERATURE),
	WIND_DIRECTION(Constant.WIND_DIRECTION, MappingConstant.LOCATION_WIND_DIRECTION),
	WIND_SPEED(Constant.WIND_SPEED, MappingConstant.LOCATION_WIND_SPEED);

	private String propertyName;
	private String propertyMapping;

	private WeatherLink(String propertyName, String propertyMapping) {
		this.propertyName = propertyName;
		this.propertyMapping = propertyMapping;
	}

	public Link createLink() {

		String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
		UriTemplate uriTemplate = UriTemplate.of(baseUri + propertyMapping);

		uriTemplate = uriTemplate.with(Constant.LOCATION_NAME, TemplateVariable.VariableType.PATH_VARIABLE);
		uriTemplate = uriTemplate.with(Constant.FROM_DATE, TemplateVariable.VariableType.REQUEST_PARAM);
		uriTemplate = uriTemplate.with(Constant.TO_DATE, TemplateVariable.VariableType.REQUEST_PARAM);

		Link link = Link.of(uriTemplate, propertyName);

		return link;
	}

	public Link createLink(String name) {

		String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
		UriTemplate uriTemplate = UriTemplate.of(baseUri + propertyMapping);
		URI uri = uriTemplate.expand(name);
		Link link = Link.of(uri.toString(), propertyName);
		return link;
	}

}
