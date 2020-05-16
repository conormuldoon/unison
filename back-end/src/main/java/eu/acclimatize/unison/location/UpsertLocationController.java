package eu.acclimatize.unison.location;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.harvester.HarvesterController;

/**
 * A controller for adding or updating a location. In contrast to the
 * {@link HarvesterController}, the controller does not make a harvest data
 * request when adding a location.
 *
 */
@RestController
public class UpsertLocationController {

	private LocationRepository locationRepository;
	private LocationService locationService;
	private UriTemplate harvestUnison;

	public UpsertLocationController(LocationRepository locationRepository, LocationService locationService,
			UriTemplate harvestUnison) {
		this.locationRepository = locationRepository;
		this.locationService = locationService;
		this.harvestUnison = harvestUnison;
	}

	/**
	 * Add a location if the location does not exist in the location repository or
	 * updates a location if the authenticated user added the location.
	 * 
	 * @param locationName The name of the location to add.
	 * @param location     The location to add.
	 */
	// @RolesAllowed is used when the location is deserialized so is not required
	// here.
	@PutMapping(MappingConstant.LOCATION)
	public URI upsert(Principal principal, @RequestBody Location location) {

		Optional<Location> optCurrent = location.findCurrent(locationRepository);

		if (optCurrent.isPresent()) {
			try {
				locationService.replace(optCurrent.get(), location);
			} catch (AccessDeniedException e) {
				
				throw new LocationUpdateException("A location can only be updated by the user that added it.");
			}
		} else {

			locationRepository.save(location);
		}
		return harvestUnison.expand(principal.getName());
	}

	public static void main(String[] args) {
		UriTemplate uriTemplate = new UriTemplate("/abc?lat={latitude}&long={longitude}");
		URI uri = uriTemplate.expand(-3.33, -4.33);
		System.out.println(uri);
	}

}
