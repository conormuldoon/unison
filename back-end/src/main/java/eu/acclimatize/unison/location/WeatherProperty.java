package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

public enum WeatherProperty {
	CLOUDINESS(Constant.CLOUDINESS), CLOUD_LEVEL(Constant.CLOUD_LEVEL), DEW_POINT(Constant.DEW_POINT),
	FOG(Constant.FOG), HUMIDITY(Constant.HUMIDITY), PRECIPITATION(Constant.PRECIPITATION), PRESSURE(Constant.PRESSURE),
	TEMPERATURE(Constant.TEMPERATURE), WIND_DIRECTION(Constant.WIND_DIRECTION), WIND_SPEED(Constant.WIND_SPEED);

	private String propertyName;

	WeatherProperty(String propertyName) {
		this.propertyName = propertyName;
	}

	public void write(JsonGenerator gen, String encodedName) throws IOException {
		gen.writeStringField(propertyName,
				MappingConstant.LOCATION + "/" + encodedName + "/" + propertyName + "{?fromDate,toDate}");
	}

}
