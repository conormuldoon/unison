package eu.acclimatize.unison;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.acclimatize.unison.result.PrecipitationResult;

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
	public ItemListFinder<PrecipitationResult> precipitationFinder(EntityManager entityManager,
			String precipitationQuery, CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, precipitationQuery, cacheSupport, PrecipitationResult.class);
	}

	/**
	 * Creates an {@link ItemListFinder} for the cloud level.
	 * 
	 * @param entityManager   The JPA entity manager.
	 * @param cloudLevelQuery The query for cloud level data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	public ItemListFinder<HarmonieItem> cloudLevelFinder(EntityManager entityManager, String cloudLevelQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, cloudLevelQuery, cacheSupport, HarmonieItem.class);
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
	public ItemListFinder<HarmonieItem> windSpeedFinder(EntityManager entityManager, String windSpeedQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, windSpeedQuery, cacheSupport, HarmonieItem.class);
	}

	/**
	 * Creates an {@link ItemListFinder} for the wind direction.
	 * 
	 * @param entityManager      The JPA entity manager.
	 * @param windDirectionQuery The query for wind direction data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	public ItemListFinder<HarmonieItem> windDirectionFinder(EntityManager entityManager, String windDirectionQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, windDirectionQuery, cacheSupport, HarmonieItem.class);
	}

	/**
	 * Creates an {@link ItemListFinder} for humidity.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param humidityQuery The query for humidity data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	public ItemListFinder<HarmonieItem> humidityFinder(EntityManager entityManager, String humidityQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, humidityQuery, cacheSupport, HarmonieItem.class);
	}

	/**
	 * Creates an {@link ItemListFinder} for fog.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param fogQuery      The query for fog data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	public ItemListFinder<HarmonieItem> fogFinder(EntityManager entityManager, String fogQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, fogQuery, cacheSupport, HarmonieItem.class);
	}

	/**
	 * Creates an {@link ItemListFinder} for cloudiness data.
	 * 
	 * @param entityManager   The JPA entity manager.
	 * @param cloudinessQuery The query for cloudiness data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder<HarmonieItem> cloudinessFinder(EntityManager entityManager, String cloudinessQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, cloudinessQuery, cacheSupport, HarmonieItem.class);
	}
	
	
	/**
	 * Creates an {@link ItemListFinder} for global radiation data.
	 * 
	 * @param entityManager   The JPA entity manager.
	 * @param cloudinessQuery The query for global radiation data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	ItemListFinder<HarmonieItem> globalRadiationFinder(EntityManager entityManager, String globalRadiationQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, globalRadiationQuery, cacheSupport, HarmonieItem.class);
	}

	/**
	 * Creates an {@link ItemListFinder} for pressure.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param pressureQuery The query for pressure data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	public ItemListFinder<HarmonieItem> pressureFinder(EntityManager entityManager, String pressureQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, pressureQuery, cacheSupport, HarmonieItem.class);
	}

	/**
	 * Creates an {@link ItemListFinder} for the dew point data.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param dewPointQuery The query for dew point data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	public ItemListFinder<HarmonieItem> dewPointFinder(EntityManager entityManager, String dewPointQuery,
			CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, dewPointQuery, cacheSupport, HarmonieItem.class);
	}

	/**
	 * Creates an {@link ItemListFinder} for temperature data.
	 * 
	 * @param entityManager    The JPA entity manager.
	 * @param temperatureQuery The query for temperature data.
	 * @return The {@link ItemListFinder} created.
	 */
	@Bean
	public ItemListFinder<HarmonieItem> temperatureFinder(EntityManager entityManager, String temperatureQuery, CacheSupport cacheSupport) {
		return new ItemListFinder<>(entityManager, temperatureQuery, cacheSupport, HarmonieItem.class);
	}

}
