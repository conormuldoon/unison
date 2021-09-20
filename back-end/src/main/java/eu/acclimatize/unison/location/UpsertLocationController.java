package eu.acclimatize.unison.location;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.RootURIBuilder;
import eu.acclimatize.unison.location.harvester.HarvestController;

/**
 * A controller for adding or updating a location. In contrast to the
 * {@link HarvestController}, the controller does not make a harvest data
 * request when adding a location.
 *
 */
@RestController
public class UpsertLocationController {

	private LocationRepository locationRepository;
	private LocationService locationService;
	private RootURIBuilder builder;

	/**
	 * Creates an instance of UpsertLocationController.
	 * 
	 * @param locationRepository The repository where the locations are stored.
	 * @param locationService    The service used to replace locations.
	 */
	public UpsertLocationController(LocationRepository locationRepository, LocationService locationService,
			RootURIBuilder builder) {
		this.locationRepository = locationRepository;
		this.locationService = locationService;
		this.builder = builder;

	}

	/**
	 * Add a location if the location does not exist in the location repository or
	 * updates a location if the authenticated user added the location.
	 * 
	 * @param location The location to add.
	 * @param response The HTTP servlet response that a location header is added to
	 *                 if the location was not already stored in the repository.
	 */
	// @RolesAllowed is used when the location is deserialized so is not required
	// here.
	@PutMapping(MappingConstant.LOCATION_COLLECTION)
	public void upsert(@RequestBody Location location, HttpServletResponse response,HttpServletRequest request) {

		Optional<Location> optCurrent = location.findCurrent(locationRepository);

		if (optCurrent.isPresent()) {
			try {
				locationService.replace(optCurrent.get(), location);
			} catch (AccessDeniedException e) {

				throw new LocationUpdateException("A location can only be updated by the user that added it.");
			}
		} else {

			locationRepository.save(location);
			response.setStatus(Constant.CREATED);
			location.addHeader(response, builder.build(request));
		}

	}

}
