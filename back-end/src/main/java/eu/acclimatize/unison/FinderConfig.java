package eu.acclimatize.unison;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration class for creating {@link ItemListFinder} beans. The
 * {@link ItemListFinder}s are created using JPQL query string beans configured
 * in {@link ResultQueryConfig}.
 *
 */
@Configuration
public class FinderConfig {

	/**
	 * Creates an {@link ItemListFinder} for precipitation.
	 * 
	 * @param entityManager      The JPA entity manager.
	 * @param precipitationQuery The query for precipitation data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder precipitationFinder(EntityManager entityManager, String precipitationQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, precipitationQuery, cacheSupport);
	}

	/**
	 * Creates an {@link ItemListFinder} for the cloud level.
	 * 
	 * @param entityManager   The JPA entity manager.
	 * @param cloudLevelQuery The query for cloud level data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder cloudLevelFinder(EntityManager entityManager, String cloudLevelQuery, CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, cloudLevelQuery, cacheSupport);
	}

	/**
	 * 
	 * Creates an {@link ItemListFinder} for the wind speed.
	 * 
	 * @param entityManager  The JPA entity manager.
	 * @param windSpeedQuery The query for wind speed data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder windSpeedFinder(EntityManager entityManager, String windSpeedQuery, CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, windSpeedQuery, cacheSupport);
	}

	/**
	 * Creates an {@link ItemListFinder} for the wind direction.
	 * 
	 * @param entityManager      The JPA entity manager.
	 * @param windDirectionQuery The query for wind direction data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder windDirectionFinder(EntityManager entityManager, String windDirectionQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, windDirectionQuery, cacheSupport);
	}

	/**
	 * Creates an {@link ItemListFinder} for humidity.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param humidityQuery The query for humidity data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder humidityFinder(EntityManager entityManager, String humidityQuery, CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, humidityQuery, cacheSupport);
	}

	/**
	 * Creates an {@link ItemListFinder} for fog.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param fogQuery      The query for fog data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder fogFinder(EntityManager entityManager, String fogQuery, CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, fogQuery, cacheSupport);
	}

	/**
	 * Creates an {@link ItemListFinder} for cloudiness data.
	 * 
	 * @param entityManager   The JPA entity manager.
	 * @param cloudinessQuery The query for cloudiness data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder cloudinessFinder(EntityManager entityManager, String cloudinessQuery, CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, cloudinessQuery, cacheSupport);
	}

	/**
	 * Creates an {@link ItemListFinder} for pressure.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param pressureQuery The query for pressure data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder pressureFinder(EntityManager entityManager, String pressureQuery, CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, pressureQuery, cacheSupport);
	}

	/**
	 * Creates an {@link ItemListFinder} for the dew point data.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param dewPointQuery The query for dew point data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder dewPointFinder(EntityManager entityManager, String dewPointQuery, CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, dewPointQuery, cacheSupport);
	}

	/**
	 * Creates an {@link ItemListFinder} for temperature data.
	 * 
	 * @param entityManager    The JPA entity manager.
	 * @param temperatureQuery The query for temperature data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder temperatureFinder(EntityManager entityManager, String temperatureQuery, CacheSupport cacheSupport) {
		return new ItemListFinder(entityManager, temperatureQuery, cacheSupport);
	}

}
