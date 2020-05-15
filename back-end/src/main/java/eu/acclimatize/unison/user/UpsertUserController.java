package eu.acclimatize.unison.user;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
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

	private UserRepository userRepository;
	private UserService userService;

	/**
	 * Creates and instance of UpsertUserController.
	 * 
	 * @param userService The service used to update the user.
	 */
	public UpsertUserController(UserRepository userRepository, UserService userService) {

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
	@PutMapping(MappingConstant.USER)
	public void upsertUser(@RequestBody UserInformation userInformation) {

		Optional<UserInformation> optCurrent = userInformation.findCurrent(userRepository);
		if (optCurrent.isPresent()) {

			try {
				userService.replace(optCurrent.get(), userInformation);
			} catch (AccessDeniedException e) {
				
				throw new UserUpdateException(
						"Users may only update their own information. The name of the user in the response body"
								+ " must match that of the authenticated user.");
			}
		} else {
			userRepository.save(userInformation);
		}

	}
}
