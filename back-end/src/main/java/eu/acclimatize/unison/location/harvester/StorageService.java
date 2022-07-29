package eu.acclimatize.unison.location.harvester;

import java.util.Collection;

import org.springframework.stereotype.Service;

import eu.acclimatize.unison.HourlyPrecipitation;
import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.HourlyWeather;
import eu.acclimatize.unison.HourlyWeatherRepository;

@Service
public class StorageService {

	private HourlyPrecipitationRepository precipitationRepository;
	private HourlyWeatherRepository weatherRepository;
	private UnknownWVRepository unknownWPRepository;

	public StorageService(HourlyPrecipitationRepository precipitationRepository,
			HourlyWeatherRepository weatherRepository, UnknownWVRepository unknownWPRepository) {

		this.weatherRepository = weatherRepository;
		this.precipitationRepository = precipitationRepository;
		this.unknownWPRepository = unknownWPRepository;

	}

	public void store(Collection<HourlyPrecipitation> hPrecipitation, Collection<HourlyWeather> hWeather) {

		precipitationRepository.saveAll(hPrecipitation);
		weatherRepository.saveAll(hWeather);

	}

	public void storeUnknown(Collection<UnknownWV> unknown) {

		unknownWPRepository.saveAll(unknown);
	}

}
