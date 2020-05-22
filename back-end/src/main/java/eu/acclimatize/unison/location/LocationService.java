package eu.acclimatize.unison.location;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import eu.acclimatize.unison.Constant;
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
	private Sort sort;

	/**
	 * Creates an instance of LocationSerivce.
	 * 
	 * @param locationRepository The repository where locations are stored.
	 * @param hpr                The repository where precipitation data is stored.
	 * @param hwr                The repository where weather data is stored.
	 */
	public LocationService(LocationRepository locationRepository, HourlyPrecipitationRepository hpr,
			HourlyWeatherRepository hwr, Sort sort) {
		this.locationRepository = locationRepository;
		this.hpr = hpr;
		this.hwr = hwr;
		this.sort = sort;

	}

	/**
	 * Delete a location and associated harvested data if the authenticated user
	 * added the location.
	 * 
	 * @param locationName The name of the location to be deleted.
	 * @param userName     The user name of the user that added the location.
	 */
	@PreAuthorize(Constant.OWNED_ITEM)
	@Transactional
	public void delete(Location ownedItem) {

		hpr.deleteForLocation(ownedItem);
		hwr.deleteForLocation(ownedItem);
		locationRepository.delete(ownedItem);
	}

	/**
	 * Only the user that added the location can update it.
	 * 
	 * @param currentLocation
	 * @param updatedLocation
	 */
	@PreAuthorize(Constant.REPLACE_ITEM)
	public void replace(Location current, Location updated) {
		locationRepository.save(updated);
	}

	public Iterable<Location> findAllSorted() {
		return locationRepository.findAll(sort);
	}

	public Location find(String locationName) {
		Location location = locationRepository.findById(locationName)
				.orElseThrow(() -> new LocationNotFoundException(locationName));
		return location;

	}

}
