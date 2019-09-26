package eu.acclimatize.unison.csvcontroller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.acclimatize.unison.ItemListFinder;

/**
 * 
 * A configuration class for creating {@link CSVResponder} beans.
 *
 */
@Configuration
public class CSVResponderConfig {

	/**
	 * Creates a {@link CVSResponder} bean for precipitation data.
	 * 
	 * @param precipitationFinder An {@link ItemListFinder} for precipitation data.
	 * @return A new instance of {@link CVSResponder} that uses the precipitation finder.
	 */
	@Bean
	CSVResponder precipitationResponder(ItemListFinder precipitationFinder) {
		return new CSVResponder(precipitationFinder);
	}

	/**
	 * Creates a {@link CVSResponder} bean for cloud level data.
	 * 
	 * @param cloudLevelFinder An {@link ItemListFinder} for cloud level data.
	 * @return A new instance of {@link CVSResponder} that uses the cloud level finder.
	 */
	@Bean
	CSVResponder cloudLevelResponder(ItemListFinder cloudLevelFinder) {
		return new CSVResponder(cloudLevelFinder);
	}

	/**
	 * Creates a {@link CVSResponder} bean for wind speed data.
	 * 
	 * @param windSpeedFinder An {@link ItemListFinder} for wind speed data.
	 * @return A new instance of {@link CVSResponder} that uses the wind speed finder.
	 */
	@Bean
	CSVResponder windSpeedResponder(ItemListFinder windSpeedFinder) {
		return new CSVResponder(windSpeedFinder);
	}

	/**
	 * Creates a {@link CVSResponder} bean for wind direction data.
	 * 
	 * @param windDirectionFinder An {@link ItemListFinder} for wind direction data.
	 * @return A new instance of {@link CVSResponder} that uses the wind direction finder.
	 */
	@Bean
	CSVResponder windDirectionResponder(ItemListFinder windDirectionFinder) {
		return new CSVResponder(windDirectionFinder);
	}

	/**
	 * Creates a {@link CVSResponder} bean for humidity data.
	 * 
	 * @param humidityFinder An {@link ItemListFinder} for humidity data.
	 * @return A new instance of {@link CVSResponder} that uses the humidity finder.
	 */
	@Bean
	CSVResponder humidityResponder(ItemListFinder humidityFinder) {
		return new CSVResponder(humidityFinder);
	}

	/**
	 * Creates a {@link CVSResponder} bean for fog data.
	 * 
	 * @param fogFinder An {@link ItemListFinder} for fog data.
	 * @return A new instance of {@link CVSResponder} that uses the fog finder.
	 */
	@Bean
	CSVResponder fogResponder(ItemListFinder fogFinder) {
		return new CSVResponder(fogFinder);
	}

	/**
	 * Creates a {@link CVSResponder} bean for cloudiness data.
	 * 
	 * @param cloudinessFinder An {@link ItemListFinder} for cloudiness data.
	 * @return A new instance of {@link CVSResponder} that uses the cloudiness finder.
	 */
	@Bean
	CSVResponder cloudinessResponder(ItemListFinder cloudinessFinder) {
		return new CSVResponder(cloudinessFinder);
	}

	/**
	 * Creates a {@link CVSResponder} bean for pressure data.
	 * 
	 * @param pressureFinder An {@link ItemListFinder} for pressure data.
	 * @return A new instance of {@link CVSResponder} that uses the pressure finder.
	 */
	@Bean
	CSVResponder pressureResponder(ItemListFinder pressureFinder) {
		return new CSVResponder(pressureFinder);
	}

	/**
	 * Creates a {@link CVSResponder} bean for dew point data.
	 * 
	 * @param dewPointFinder An {@link ItemListFinder} for dew point data.
	 * @return A new instance of {@link CVSResponder} that uses the dew point finder.
	 */
	@Bean
	CSVResponder dewPointResponder(ItemListFinder dewPointFinder) {
		return new CSVResponder(dewPointFinder);
	}

	/**
	 * Creates a {@link CVSResponder} bean for temperature data.
	 * 
	 * @param temperatureFinder An {@link ItemListFinder} for temperature data.
	 * @return A new instance of {@link CVSResponder} that uses the temperature finder.
	 */
	@Bean
	CSVResponder temperatureResponder(ItemListFinder temperatureFinder) {
		return new CSVResponder(temperatureFinder);
	}

}
