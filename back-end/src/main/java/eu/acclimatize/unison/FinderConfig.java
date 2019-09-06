package eu.acclimatize.unison;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinderConfig {

	@Bean
	ItemListFinder precipitationFinder(EntityManager entityManager, String precipitationQuery) {
		return new ItemListFinder(entityManager, precipitationQuery);
	}
	
	@Bean
	ItemListFinder cloudLevelFinder(EntityManager entityManager, String cloudLevelQuery) {
		return new ItemListFinder(entityManager, cloudLevelQuery);
	}
	
	@Bean
	ItemListFinder windSpeedFinder(EntityManager entityManager, String windSpeedQuery) {
		return new ItemListFinder(entityManager, windSpeedQuery);
	}
	
	@Bean
	ItemListFinder windDirectionFinder(EntityManager entityManager, String windDirectionQuery) {
		return new ItemListFinder(entityManager, windDirectionQuery);
	}

	
	@Bean
	ItemListFinder humidityFinder(EntityManager entityManager, String humidityQuery) {
		return new ItemListFinder(entityManager, humidityQuery);
	}

	
	@Bean
	ItemListFinder fogFinder(EntityManager entityManager, String fogQuery) {
		return new ItemListFinder(entityManager, fogQuery);
	}

	
	@Bean
	ItemListFinder cloudinessFinder(EntityManager entityManager, String cloudinessQuery) {
		return new ItemListFinder(entityManager, cloudinessQuery);
	}
	
	@Bean
	ItemListFinder pressureFinder(EntityManager entityManager, String pressureQuery) {
		return new ItemListFinder(entityManager, pressureQuery);
	}

	
	@Bean
	ItemListFinder dewPointFinder(EntityManager entityManager, String dewPointQuery) {
		return new ItemListFinder(entityManager, dewPointQuery);
	}
	
	@Bean
	ItemListFinder temperatureFinder(EntityManager entityManager, String temperatureQuery) {
		return new ItemListFinder(entityManager, temperatureQuery);
	}

}

