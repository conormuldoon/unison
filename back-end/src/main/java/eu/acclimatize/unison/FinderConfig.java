package eu.acclimatize.unison;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration class for creating {@link ItemListFinder} beans.
 *
 */
@Configuration
public class FinderConfig {

	/**
	 * Creates an {@link ItemListFinder} for precipitation.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param precipitationQuery The query for precipitation data.
	 * @return
	 */
	@Bean
	ItemListFinder precipitationFinder(EntityManager entityManager, String precipitationQuery) {
		return new ItemListFinder(entityManager, precipitationQuery);
	}
	
	/**
	 * Creates an {@link ItemListFinder} for the cloud level.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param cloudLevelQuery The query for cloud level data.
	 * @return
	 */
	@Bean
	ItemListFinder cloudLevelFinder(EntityManager entityManager, String cloudLevelQuery) {
		return new ItemListFinder(entityManager, cloudLevelQuery);
	}
	
	/**
	 * 
	 * Creates an {@link ItemListFinder} for the wind speed.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param windSpeedQuery The query for wind speed data.
	 * @return
	 */
	@Bean
	ItemListFinder windSpeedFinder(EntityManager entityManager, String windSpeedQuery) {
		return new ItemListFinder(entityManager, windSpeedQuery);
	}
	
	/**
	 * Creates an {@link ItemListFinder} for the wind direction.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param windDirectionQuery The query for wind direction data.
	 * @return
	 */
	@Bean
	ItemListFinder windDirectionFinder(EntityManager entityManager, String windDirectionQuery) {
		return new ItemListFinder(entityManager, windDirectionQuery);
	}

	/**
	 * Creates an {@link ItemListFinder} for humidity.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param humidityQuery The query for humidity data.
	 * @return
	 */
	@Bean
	ItemListFinder humidityFinder(EntityManager entityManager, String humidityQuery) {
		return new ItemListFinder(entityManager, humidityQuery);
	}

	/**
	 * Creates an {@link ItemListFinder} for fog.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param fogQuery The query for fog data.
	 * @return
	 */
	@Bean
	ItemListFinder fogFinder(EntityManager entityManager, String fogQuery) {
		return new ItemListFinder(entityManager, fogQuery);
	}

	/**
	 * Creates an {@link ItemListFinder} for cloudiness.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param cloudinessQuery The query for cloudiness data.
	 * @return
	 */
	@Bean
	ItemListFinder cloudinessFinder(EntityManager entityManager, String cloudinessQuery) {
		return new ItemListFinder(entityManager, cloudinessQuery);
	}
	
	/**
	 * Creates an {@link ItemListFinder} for pressure.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param pressureQuery The query for pressure data.
	 * @return
	 */
	@Bean
	ItemListFinder pressureFinder(EntityManager entityManager, String pressureQuery) {
		return new ItemListFinder(entityManager, pressureQuery);
	}


	/**
	 * Creates an {@link ItemListFinder} for the dew point.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param dewPointQuery The query for dew point data.
	 * @return
	 */
	@Bean
	ItemListFinder dewPointFinder(EntityManager entityManager, String dewPointQuery) {
		return new ItemListFinder(entityManager, dewPointQuery);
	}
	
	/**
	 * Creates an {@link ItemListFinder} for temperature.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param temperatureQuery The query for temperature data.
	 * @return
	 */
	@Bean
	ItemListFinder temperatureFinder(EntityManager entityManager, String temperatureQuery) {
		return new ItemListFinder(entityManager, temperatureQuery);
	}

}

