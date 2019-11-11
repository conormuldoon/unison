package eu.acclimatize.unison.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.ResponseConstant;

/**
 * 
 * A controller for updating a user's password.
 *
 */
@RestController
public class UpdatePasswordController {

	private static final String UPDATE_PASSWORD = "/updatePassword";

	UserRepository userRepository;

	UserService userService;

	BCryptPasswordEncoder passwordEncoder;

	/**
	 * Creates and instance of UpdatePasswordController.
	 * 
	 * @param userRepository  The repository that stores user information.
	 * @param userService     A service that enables takes to be executed that
	 *                        require user credentials.
	 * @param passwordEncoder Used to encrypt new passwords.
	 */
	public UpdatePasswordController(UserRepository userRepository, UserService userService,
			BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;

	}

	/**
	 * Updates the user's password if the current credentials specified are correct.
	 * 
	 * @param userName    The user name of an existing user.
	 * @param password    The password of the user.
	 * @param newPassword The new password for the user.
	 * @return The {@link eu.acclimatize.unison.ResponseConstant} value for the
	 *         result of the request to update the password.
	 */
	@PostMapping(UPDATE_PASSWORD)
	public int updatePassword(@RequestParam(Constant.USERNAME) String userName,
			@RequestParam(Constant.PASSWORD) String password, @RequestParam(Constant.NEW_PASSWORD) String newPassword) {

		UserTask task = new CredentialsTask(userName, newPassword, passwordEncoder, userRepository);
		return userService.executeTask(userName, password, task);
	}

}
