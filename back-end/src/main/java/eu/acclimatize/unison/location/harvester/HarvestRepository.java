package eu.acclimatize.unison.location.harvester;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import eu.acclimatize.unison.HourlyPrecipitation;
import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.HourlyWeather;
import eu.acclimatize.unison.HourlyWeatherRepository;

/**
 * A repository for storing harvested data. The repository acts as a wrapper
 * around the precipitation, general weather, unknown weather variable
 * repositories. Unknown weather variables are variables added to the harvested
 * end-point by the meteorological service post hoc.
 *
 */
@Repository
public class HarvestRepository {

	private HourlyPrecipitationRepository precipitationRepository;
	private HourlyWeatherRepository weatherRepository;
	private UnknownWVRepository unknownWVRepository;

	/**
	 * Creates and instance of HarvestRespoitroy.
	 * 
	 * @param precipitationRepository The repository for storing precipitation data.
	 * @param weatherRepository       The repository for storing general weather
	 *                                data (other than precipitation data).
	 * @param unknownWVRepository     The repository for storing unknown weather
	 *                                variable data.
	 */
	public HarvestRepository(HourlyPrecipitationRepository precipitationRepository,
			HourlyWeatherRepository weatherRepository, UnknownWVRepository unknownWVRepository) {

		this.weatherRepository = weatherRepository;
		this.precipitationRepository = precipitationRepository;
		this.unknownWVRepository = unknownWVRepository;

	}

	/**
	 * Stores weather data and data for unknown weather variables.
	 * 
	 * @param hPrecipitation A collection of precipitation data.
	 * @param hWeather       A collection of general weather data.
	 * @param unknown        A collection of data for unknown weather variables.
	 */
	@Transactional
	public void store(Collection<HourlyPrecipitation> hPrecipitation, Collection<HourlyWeather> hWeather,
			Collection<UnknownWV> unknown) {

		precipitationRepository.saveAll(hPrecipitation);
		weatherRepository.saveAll(hWeather);
		unknownWVRepository.saveAll(unknown);

	}

}
