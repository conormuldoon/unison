package eu.acclimatize.unison;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.acclimatize.unison.result.CloudLevelResult;
import eu.acclimatize.unison.result.CloudinessResult;
import eu.acclimatize.unison.result.DewPointResult;
import eu.acclimatize.unison.result.FogResult;
import eu.acclimatize.unison.result.GlobalRadiationResult;
import eu.acclimatize.unison.result.HumidityResult;
import eu.acclimatize.unison.result.PrecipitationResult;
import eu.acclimatize.unison.result.PressureResult;
import eu.acclimatize.unison.result.TemperatureResult;
import eu.acclimatize.unison.result.WindDirectionResult;
import eu.acclimatize.unison.result.WindSpeedResult;

/**
 * 
 * A configuration class for creating beans for JPQL queries. The query results
 * are order by time (fromHour column in the database).
 *
 */
@Configuration
public class ResultQueryConfig {

	private static final String VALUE = "value";

	private static final String WEA_TABLE = HourlyWeather.class.getSimpleName();

	private static final String PRE_TABLE = HourlyPrecipitation.class.getSimpleName();

	private String createQuery(String entityName, Class<? extends HarmonieItem> resultClass, String item) {
		return "select new " + resultClass.getName() + "(a.key.fromHour,a." + item + ") from " + entityName
				+ " a where a.key.location.name = :" + Constant.LOCATION_NAME + " and a.key.fromHour >= :"
				+ Constant.FROM_DATE + " and cast(a.key.fromHour as date) <= :" + Constant.TO_DATE
				+ " order by a.key.fromHour";

	}

	/**
	 * Creates a JPQL query for precipitation.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String precipitationQuery() {
		return createQuery(PRE_TABLE, PrecipitationResult.class, VALUE);
	}

	/**
	 * Creates a JPQL query for the cloud level.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String cloudLevelQuery() {
		return createQuery(WEA_TABLE, CloudLevelResult.class, VALUE + ".cloud");
	}

	/**
	 * Creates a JPQL query for the wind speed.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String windSpeedQuery() {
		return createQuery(WEA_TABLE, WindSpeedResult.class, VALUE + ".windSpeed");
	}

	/**
	 * Creates a JPQL query for the wind direction.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String windDirectionQuery() {
		return createQuery(WEA_TABLE, WindDirectionResult.class, VALUE + ".windDirection");
	}

	/**
	 * Creates a JPQL query for humidity.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String humidityQuery() {
		return createQuery(WEA_TABLE, HumidityResult.class, VALUE + ".humidity");
	}

	/**
	 * Creates a JPQL query for fog.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String fogQuery() {
		return createQuery(WEA_TABLE, FogResult.class, VALUE + ".fog");
	}

	/**
	 * Creates a JPQL query for cloudiness.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String cloudinessQuery() {
		return createQuery(WEA_TABLE, CloudinessResult.class, VALUE + ".cloudiness");
	}
	
	/**
	 * Creates a JPQL query for global radiation.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String globalRadiationQuery() {
		return createQuery(WEA_TABLE, GlobalRadiationResult.class, VALUE + ".cloudiness");
	}

	/**
	 * Creates a JPQL query for pressure.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String pressureQuery() {
		return createQuery(WEA_TABLE, PressureResult.class, VALUE + ".pressure");
	}

	/**
	 * Creates a JPQL query for the dew point.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String dewPointQuery() {
		return createQuery(WEA_TABLE, DewPointResult.class, VALUE + ".dewPoint");
	}

	/**
	 * Creates a JPQL query for temperature.
	 * 
	 * @return A parameterized query string with parameters for the location, from
	 *         date, and to date.
	 */
	@Bean
	public String temperatureQuery() {
		return createQuery(WEA_TABLE, TemperatureResult.class, VALUE + ".temperature");
	}

}
