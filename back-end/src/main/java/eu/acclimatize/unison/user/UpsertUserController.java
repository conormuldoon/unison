package eu.acclimatize.unison.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.MappingConstant;

/**
 * A controller for adding or updating the authenticated user.
 *
 */
@RestController
public class UpsertUserController {

	private final static String USER_NAME = "userName";

	private UserRepository userRepository;
	private UserService userService;

	/**
	 * Creates and instance of UpsertUserController.
	 * 
	 * @param userService The service used to update the user.
	 */
	public UpsertUserController(UserService userService, UserRepository userRepository) {

		this.userRepository = userRepository;
		this.userService = userService;

	}

	/**
	 * Updates the authenticated user or stores new user information if there are no
	 * users with the same user name. The user can only update their own
	 * information.
	 * 
	 * @param principal The user name for the update.
	 * @param password  The updated user information.
	 */
	// The roles allowed annotation is not required here as MappingConstant.USER is
	// configured to require authorization in the UnisonSecurityConfig
	// configuration.
	@PutMapping(MappingConstant.USER + "/{" + USER_NAME + "}")
	public void upsertUser(@PathVariable(USER_NAME) String userName, @RequestBody UserInformation userInformation) {

		if (userInformation.existsIn(userRepository)) {

			userService.update(userName, userInformation);
		} else {
			userRepository.save(userInformation);
		}

	}
}
