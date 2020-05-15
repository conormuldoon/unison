package eu.acclimatize.unison.location;

import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.HourlyWeatherRepository;

/**
 * A service that is used to delete locations.
 *
 */
@Service
public class LocationService {

	private LocationRepository locationRepository;
	private HourlyPrecipitationRepository hpr;
	private HourlyWeatherRepository hwr;

	/**
	 * Creates an instance of LocationSerivce.
	 * 
	 * @param locationRepository The repository where locations are stored.
	 * @param hpr                The repository where precipitation data is stored.
	 * @param hwr                The repository where weather data is stored.
	 */
	public LocationService(LocationRepository locationRepository, HourlyPrecipitationRepository hpr,
			HourlyWeatherRepository hwr) {
		this.locationRepository = locationRepository;
		this.hpr = hpr;
		this.hwr = hwr;

	}

	/**
	 * Delete a location and associated harvested data if the authenticated user
	 * added the location.
	 * 
	 * @param locationName The name of the location to be deleted.
	 * @param userName     The user name of the user that added the location.
	 */
	@PreAuthorize("#location.hasOwner(authentication.name)")
	@Transactional
	public void delete(Location location) {

		hpr.deleteForLocation(location);
		hwr.deleteForLocation(location);
		locationRepository.delete(location);
	}

	/**
	 * Only the user that added the location can update it.
	 * 
	 * @param currentLocation
	 * @param updatedLocation
	 */
	@PreAuthorize("#current.hasOwner(authentication.name)")
	public void replace(Location current, Location updated) {
		locationRepository.save(updated);
	}

}
