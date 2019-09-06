package eu.acclimatize.unison.csvcontroller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.acclimatize.unison.ItemListFinder;

@Configuration
public class CSVResponderConfig {

	@Bean
	CSVResponder precipitationResponder(ItemListFinder precipitationFinder) {
		return new CSVResponder(precipitationFinder);
	}

	@Bean
	CSVResponder cloudLevelResponder(ItemListFinder cloudLevelFinder) {
		return new CSVResponder(cloudLevelFinder);
	}

	@Bean
	CSVResponder windSpeedResponder(ItemListFinder windSpeedFinder) {
		return new CSVResponder(windSpeedFinder);
	}

	@Bean
	CSVResponder windDirectionResponder(ItemListFinder windDirectionFinder) {
		return new CSVResponder(windDirectionFinder);
	}

	@Bean
	CSVResponder humidityResponder(ItemListFinder humidityFinder) {
		return new CSVResponder(humidityFinder);
	}

	@Bean
	CSVResponder fogResponder(ItemListFinder fogFinder) {
		return new CSVResponder(fogFinder);
	}

	@Bean
	CSVResponder cloudinessResponder(ItemListFinder cloudinessFinder) {
		return new CSVResponder(cloudinessFinder);
	}

	@Bean
	CSVResponder pressureResponder(ItemListFinder pressureFinder) {
		return new CSVResponder(pressureFinder);
	}

	@Bean
	CSVResponder dewPointResponder(ItemListFinder dewPointFinder) {
		return new CSVResponder(dewPointFinder);
	}

	@Bean
	CSVResponder temperatureResponder(ItemListFinder temperatureFinder) {
		return new CSVResponder(temperatureFinder);
	}

}
