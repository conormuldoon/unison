package eu.acclimatize.unison;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.acclimatize.unison.result.CloudLevelResult;
import eu.acclimatize.unison.result.CloudinessResult;
import eu.acclimatize.unison.result.DewPointResult;
import eu.acclimatize.unison.result.FogResult;
import eu.acclimatize.unison.result.HumidityResult;
import eu.acclimatize.unison.result.PrecipitationResult;
import eu.acclimatize.unison.result.PressureResult;
import eu.acclimatize.unison.result.TemperatureResult;
import eu.acclimatize.unison.result.WindDirectionResult;
import eu.acclimatize.unison.result.WindSpeedResult;

@Configuration
public class ResultQueryConfig {


	private final static String VALUE = "value";

	private final static String WEA_TABLE = HourlyWeather.class.getSimpleName();

	private final static String PRE_TABLE = HourlyPrecipitation.class.getSimpleName();

	private String createQuery(String entityName, Class<? extends HarmonieItem> resultClass, String item) {
		String query = "select new " + resultClass.getName() + "(a.key.fromHour,a." + item + ") from " + entityName
				+ " a where a.key.location.name= :" + Constant.LOCATION + " and a.key.fromHour >= :" + Constant.FROM_DATE
				+ " and cast(a.key.fromHour as date) <= :" + Constant.TO_DATE + " order by a.key.fromHour";
		return query;
	}
	
	@Bean
	public String precipitationQuery() {
		return createQuery(PRE_TABLE, PrecipitationResult.class, VALUE);
	}

	@Bean
	public String cloudLevelQuery() {
		return createQuery(WEA_TABLE, CloudLevelResult.class, VALUE + ".cloud");
	}

	@Bean
	public String windSpeedQuery() {
		return createQuery(WEA_TABLE, WindSpeedResult.class, VALUE + ".windSpeed");
	}

	@Bean
	public String windDirectionQuery() {
		return createQuery(WEA_TABLE, WindDirectionResult.class, VALUE + ".windDirection");
	}

	@Bean
	public String humidityQuery() {
		return createQuery(WEA_TABLE, HumidityResult.class, VALUE + ".humidity");
	}

	@Bean
	public String fogQuery() {
		return createQuery(WEA_TABLE, FogResult.class, VALUE + ".fog");
	}

	@Bean
	public String cloudinessQuery() {
		return createQuery(WEA_TABLE, CloudinessResult.class, VALUE + ".cloudiness");
	}

	@Bean
	public String pressureQuery() {
		return createQuery(WEA_TABLE, PressureResult.class, VALUE + ".pressure");
	}

	@Bean
	public String dewPointQuery() {
		return createQuery(WEA_TABLE, DewPointResult.class, VALUE + ".dewPoint");
	}

	@Bean
	public String temperatureQuery() {
		return createQuery(WEA_TABLE, TemperatureResult.class, VALUE + ".temperature");
	}

}
