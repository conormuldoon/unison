package eu.acclimatize.unison.location;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.HourlyWeatherRepository;

/**
 * A service that is used to find, to replace, and to delete locations.
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
	 * @param sort               Determines the order in which locations with be
	 *                           ordered when finding all stored locations.
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
	 * @param ownedItem The location to be deleted.
	 */
	@PreAuthorize(Constant.OWNED_ITEM)
	@Transactional
	public void delete(Location ownedItem) {

		hpr.deleteForLocation(ownedItem);
		hwr.deleteForLocation(ownedItem);
		locationRepository.delete(ownedItem);
	}

	/**
	 * Replaces the current location with the updated location. Only the user that
	 * added the location can update it.
	 * 
	 * 
	 * @param current The location stored in the repository.
	 * @param updated The new location with the same name.
	 */
	@PreAuthorize(Constant.REPLACE_ITEM)
	public void replace(Location current, Location updated) {
		locationRepository.save(updated);
	}

	/**
	 * Finds all locations in a sorted order.
	 * 
	 * @return The locations found.
	 */
	public Iterable<Location> findAllSorted() {
		return locationRepository.findAll(sort);
	}

	/**
	 * Finds a specific location. If a location of the specified name is not present
	 * a location not found exception will be thrown.
	 * 
	 * @param locationName The name of the location to find.
	 * @return The location found.
	 */
	public Location find(String locationName) {

		return locationRepository.findById(locationName).orElseThrow(() -> new LocationNotFoundException(locationName));

	}

}
