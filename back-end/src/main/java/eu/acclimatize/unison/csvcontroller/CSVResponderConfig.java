package eu.acclimatize.unison.csvcontroller;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.acclimatize.unison.CacheSupport;
import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.ItemListFinder;
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

/**
 * 
 * A configuration class for creating {@link CSVResponder} beans.
 *
 */
@Configuration
public class CSVResponderConfig {

	private static final char DELIMITER = ',';

	private DateFormat dateFormat;

	/**
	 * Creates an instance of CSVResponder.
	 */
	public CSVResponderConfig() {
		dateFormat = new SimpleDateFormat(Constant.FORMAT);
	}

	// Recursively appends the double, Double, int, Date, and String CSVHeaderItem
	// values of the attributes of the result classes to create a CSV header string.
	private void appendFieldNames(Class<?> c, StringBuilder sb) {
		Field[] fieldArr = c.getDeclaredFields();

		for (Field f : fieldArr) {
			CSVHeaderItem csvProperty = f.getAnnotation(CSVHeaderItem.class);
			if (csvProperty != null) {
				Class<?> ft = f.getType();
				if (ft.equals(Date.class) || ft.equals(Double.TYPE) || ft.equals(Integer.TYPE)
						|| ft.equals(Double.class) || ft.equals(String.class)) {
					String value = csvProperty.value();

					if (value.equals(Constant.CSV_HEADER_DEFAULT))
						sb.append(f.getName());
					else
						sb.append(value);

					sb.append(DELIMITER);
				} else {
					appendFieldNames(ft, sb);
				}
			}
		}
	}

	private String createHeader(Class<?> c) {
		StringBuilder sb = new StringBuilder();
		appendFieldNames(c, sb);
		return sb.toString();
	}

	/**
	 * Creates a {@link CSVResponder} bean for precipitation data.
	 * 
	 * @param precipitationFinder An {@link ItemListFinder} for precipitation data.
	 * @param cacheSupport        Used for adding conditional the Vary header and a
	 *                            conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the precipitation
	 *         finder.
	 */
	@Bean
	public CSVResponder precipitationResponder(ItemListFinder<PrecipitationResult> precipitationFinder,
			CacheSupport cacheSupport) {
		return new CSVResponder(precipitationFinder, createHeader(PrecipitationResult.class), cacheSupport,
				"Precipitation", dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for cloud level data.
	 * 
	 * @param cloudLevelFinder An {@link ItemListFinder} for cloud level data.
	 * @param cacheSupport     Used for adding conditional the Vary header and a
	 *                         conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the cloud level
	 *         finder.
	 */
	@Bean
	public CSVResponder cloudLevelResponder(ItemListFinder<HarmonieItem> cloudLevelFinder, CacheSupport cacheSupport) {
		return new CSVResponder(cloudLevelFinder, createHeader(CloudLevelResult.class), cacheSupport, "Cloud_Level",
				dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for wind speed data.
	 * 
	 * @param windSpeedFinder An {@link ItemListFinder} for wind speed data.
	 * @param cacheSupport    Used for adding conditional the Vary header and a
	 *                        conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the wind speed
	 *         finder.
	 */
	@Bean
	public CSVResponder windSpeedResponder(ItemListFinder<HarmonieItem> windSpeedFinder, CacheSupport cacheSupport) {
		return new CSVResponder(windSpeedFinder, createHeader(WindSpeedResult.class), cacheSupport, "Wind_Speed",
				dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for wind direction data.
	 * 
	 * @param windDirectionFinder An {@link ItemListFinder} for wind direction data.
	 * @param cacheSupport        Used for adding conditional the Vary header and a
	 *                            conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the wind direction
	 *         finder.
	 */
	@Bean
	public CSVResponder windDirectionResponder(ItemListFinder<HarmonieItem> windDirectionFinder,
			CacheSupport cacheSupport) {
		return new CSVResponder(windDirectionFinder, createHeader(WindDirectionResult.class), cacheSupport,
				"Wind_Direction", dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for humidity data.
	 * 
	 * @param humidityFinder An {@link ItemListFinder} for humidity data.
	 * @param cacheSupport   Used for adding conditional the Vary header and a
	 *                       conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the humidity finder.
	 */
	@Bean
	public CSVResponder humidityResponder(ItemListFinder<HarmonieItem> humidityFinder, CacheSupport cacheSupport) {
		return new CSVResponder(humidityFinder, createHeader(HumidityResult.class), cacheSupport, "Humdity",
				dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for globalRadiation data.
	 * 
	 * @param globalRadiationFinder An {@link ItemListFinder} for global radiation
	 *                              data.
	 * @param cacheSupport          Used for adding conditional the Vary header and
	 *                              a conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the global radiation
	 *         finder.
	 */
	@Bean
	public CSVResponder globalRadiationResponder(ItemListFinder<HarmonieItem> globalRadiationFinder,
			CacheSupport cacheSupport) {
		return new CSVResponder(globalRadiationFinder, createHeader(HumidityResult.class), cacheSupport,
				"Global_Radiation", dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for fog data.
	 * 
	 * @param fogFinder    An {@link ItemListFinder} for fog data. * @param
	 *                     cacheSupport Used for adding conditional the Vary header
	 *                     and a conditional cache control header.
	 * 
	 * @param cacheSupport Used for adding conditional the Vary header and a
	 *                     conditional cache control header.
	 * 
	 * @return A new instance of {@link CSVResponder} that uses the fog finder.
	 */
	@Bean
	public CSVResponder fogResponder(ItemListFinder<HarmonieItem> fogFinder, CacheSupport cacheSupport) {
		return new CSVResponder(fogFinder, createHeader(FogResult.class), cacheSupport, "Fog", dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for cloudiness data.
	 * 
	 * @param cloudinessFinder An {@link ItemListFinder} for cloudiness data.
	 * 
	 * @param cacheSupport     Used for adding conditional the Vary header and a
	 *                         conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the cloudiness
	 *         finder.
	 */
	@Bean
	public CSVResponder cloudinessResponder(ItemListFinder<HarmonieItem> cloudinessFinder, CacheSupport cacheSupport) {
		return new CSVResponder(cloudinessFinder, createHeader(CloudinessResult.class), cacheSupport, "Cloudiness",
				dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for pressure data.
	 * 
	 * @param pressureFinder An {@link ItemListFinder} for pressure data.
	 * 
	 * @param cacheSupport   Used for adding conditional the Vary header and a
	 *                       conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the pressure finder.
	 */
	@Bean
	public CSVResponder pressureResponder(ItemListFinder<HarmonieItem> pressureFinder, CacheSupport cacheSupport) {
		return new CSVResponder(pressureFinder, createHeader(PressureResult.class), cacheSupport, "Pressure",
				dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for dew point data.
	 * 
	 * @param dewPointFinder An {@link ItemListFinder} for dew point data.
	 * @param cacheSupport   Used for adding conditional the Vary header and a
	 *                       conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the dew point
	 *         finder.
	 */
	@Bean
	public CSVResponder dewPointResponder(ItemListFinder<HarmonieItem> dewPointFinder, CacheSupport cacheSupport) {
		return new CSVResponder(dewPointFinder, createHeader(DewPointResult.class), cacheSupport, "Dew Point",
				dateFormat);
	}

	/**
	 * Creates a {@link CSVResponder} bean for temperature data.
	 * 
	 * @param temperatureFinder An {@link ItemListFinder} for temperature data.
	 * @param cacheSupport      Used for adding conditional the Vary header and a
	 *                          conditional cache control header.
	 * @return A new instance of {@link CSVResponder} that uses the temperature
	 *         finder.
	 */
	@Bean
	public CSVResponder temperatureResponder(ItemListFinder<HarmonieItem> temperatureFinder,
			CacheSupport cacheSupport) {
		return new CSVResponder(temperatureFinder, createHeader(TemperatureResult.class), cacheSupport, "Temperature",
				dateFormat);
	}

}
