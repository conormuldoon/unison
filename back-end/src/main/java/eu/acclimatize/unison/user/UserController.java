package eu.acclimatize.unison.user;

import java.security.Principal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.ResponseConstant;

/**
 * 
 * A controller for adding new users and updating passwords.
 *
 */
@RestController
public class UserController {

	private UserService userService;
	private UserRepository userRepository;

	/**
	 * Creates a new instance of UserController.
	 * 
	 * @param userService    The service used to encode passwords and save user
	 *                       information.
	 * @param userRepository The repository that contains user information.
	 */
	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;

	}

	/**
	 * Adds new user information to the {@link UserRepository}.
	 * 
	 * @param userInformation The user information to store.
	 * @return {@value eu.acclimatize.unison.ResponseConstant#SUCCESS} if the user
	 *         information was stored and
	 *         {@value eu.acclimatize.unison.ResponseConstant#FAILURE} if a user
	 *         with the same user name already has been stored.
	 */
	@PostMapping(Constant.ADD_USER_MAPPING)
	public int addUser(@RequestBody UserInformation userInformation) {

		if (userInformation.existIn(userRepository)) {
			return ResponseConstant.FAILURE;
		} else {
			userService.encodeAndSave(userInformation);
			return ResponseConstant.SUCCESS;
		}

	}

	/**
	 * Updates the password of the authenticated user.
	 * 
	 * @param principal The Spring Security principal for the authenticated user.
	 * @param password  The new password for the user.
	 */
	@PostMapping(Constant.UPDATE_PASSWORD_MAPPING)
	public void updatePassword(Principal principal, @RequestBody String password) {
		String userName = principal.getName();
		userService.encodeAndSave(new UserInformation(userName, password));
	}

}
