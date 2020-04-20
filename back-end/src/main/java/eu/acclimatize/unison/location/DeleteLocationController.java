package eu.acclimatize.unison.location;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.HourlyWeatherRepository;
import eu.acclimatize.unison.ResponseConstant;
import eu.acclimatize.unison.user.UserService;
import eu.acclimatize.unison.user.UserTask;

/**
 * 
 * A controller to delete a location being tracked. The precipitation and weather data associated with
 * the location will also be deleted.
 *
 */
@RestController
public class DeleteLocationController {

	private static final String DELETE_LOCATION = "/deleteLocation";

	private LocationRepository locationRepository;
	private HourlyPrecipitationRepository hpr;
	private HourlyWeatherRepository hwr;
	private UserService userService;

	/**
	 * Creates an instance of DeleteLocationController.
	 * 
	 * @param locationRepository The repository where locations are stored.
	 * @param hpr The repository where precipitation data is stored.
	 * @param hwr The repository where weather data is stored.
	 * @param userService A service that enables takes to be executed that require user credentials.
	 */
	public DeleteLocationController(LocationRepository locationRepository, HourlyPrecipitationRepository hpr,
			HourlyWeatherRepository hwr,  UserService userService) {
		this.locationRepository = locationRepository;
		this.hpr = hpr;
		this.hwr = hwr;
		this.userService = userService;

	}

	/** Delete a location from the database.
	 * 
	 * @param locationName The name of the location to delete.
	 * @param userName The user name credential.
	 * @param password The password credential.
	 * @return 0 if failed to delete the location, 1 if successfully removed the location, 2 if incorrect credentials used, 
	 * and 3 if the user used correct credentials, but did not add the location.
	 */
	@DeleteMapping(DELETE_LOCATION)
	public int deleteLocation(@RequestParam(Constant.LOCATION) String locationName,
			@RequestParam(Constant.USERNAME) String userName, @RequestParam(Constant.PASSWORD) String password) {

		UserTask task = user -> {

			if(locationRepository.existsById(locationName)) {
				String owner=locationRepository.findOwner(locationName);
				if(owner.equals(userName)) {
					hpr.deleteForLocation(locationName);
					hwr.deleteForLocation(locationName);
					locationRepository.deleteById(locationName);
					return ResponseConstant.SUCCESS;

				} else {
					return ResponseConstant.NOT_OWNER;

				}
				
			}else {
				return ResponseConstant.FAIL;
			}
			
		};

		return userService.executeTask(userName, password, task);

	}
}
