package eu.acclimatize.unison.location;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.HourlyWeatherRepository;
import eu.acclimatize.unison.user.UserService;
import eu.acclimatize.unison.user.UserTask;

@RestController
public class DeleteLocationController {

	private static final String DELETE_LOCATION = "/deleteLocation";

	private LocationRepository locationRepository;
	private HourlyPrecipitationRepository hpr;
	private HourlyWeatherRepository hwr;
	private CoordinatesStore store;
	private UserService userService;

	public DeleteLocationController(LocationRepository locationRepository, HourlyPrecipitationRepository hpr,
			HourlyWeatherRepository hwr, CoordinatesStore store, UserService userService) {
		this.locationRepository = locationRepository;
		this.hpr = hpr;
		this.hwr = hwr;
		this.store = store;
		this.userService = userService;

	}

	@PostMapping(DELETE_LOCATION)
	public int deleteLocation(@RequestParam(Constant.LOCATION) String locationName,
			@RequestParam(Constant.USERNAME) String userName, @RequestParam(Constant.PASSWORD) String password) {

		UserTask task = (user) -> {

			if(locationRepository.existsById(locationName)) {
				String owner=locationRepository.findOwner(locationName);
				if(owner.equals(userName)) {
					hpr.deleteForLocation(locationName);
					hwr.deleteForLocation(locationName);
					store.delete(locationName);
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
