package eu.acclimatize.unison.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.ResponseConstant;

/**
 * 
 * A controller for adding new users.
 *
 */
@RestController
public class AddUserController {

	private static final String ADD_USER = "/addUser";

	private UserRepository userRepository;

	private UserService userService;

	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Creates a new instance of AddUserController.
	 * 
	 * @param userRepository  The repository that stores user information.
	 * @param userService     A service that enables takes to be executed that
	 *                        require user credentials.
	 * @param passwordEncoder Used to encrypt new users' passwords.
	 */
	public AddUserController(UserRepository userRepository, UserService userService,
			BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;

	}

	/**
	 * Add a new user to the {@link UserRepository}
	 * 
	 * @param userName    The user name of an existing user.
	 * @param password    The password of an existing user.
	 * @param newUserName The user name of the new user.
	 * @param newPassword The password of the new user.
	 * @return The {@link eu.acclimatize.unison.ResponseConstant} value for the
	 *         result of the request to add a user.
	 */
	@PostMapping(ADD_USER)
	public int addUser(@RequestParam(Constant.USERNAME) String userName,
			@RequestParam(Constant.PASSWORD) String password, @RequestParam(Constant.NEW_USERNAME) String newUserName,
			@RequestParam(Constant.NEW_PASSWORD) String newPassword) {

		if (userRepository.existsById(newUserName)) {
			return ResponseConstant.FAIL;
		}

		UserTask task = new CredentialsTask(newUserName, newPassword, passwordEncoder, userRepository);
		return userService.executeTask(userName, password, task);
	}

}
