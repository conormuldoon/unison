package eu.acclimatize.unison.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.ResponseConstant;

/**
 * 
 * A controller for storing information for new users.
 *
 */
@RestController
public class AddUserController {

	private UserRepository userRepository;

	/**
	 * Creates a new instance of AddUserController.
	 * 
	 * @param userRepository The repository where user information is stored.
	 */
	public AddUserController(UserRepository userRepository) {

		this.userRepository = userRepository;

	}

	/**
	 * Adds new user information to the {@link UserRepository} if information for a
	 * user with the same user name was not previously stored.
	 * 
	 * @param userInformation The user information to store.
	 * @return {@value eu.acclimatize.unison.ResponseConstant#SUCCESS} if the user
	 *         information was stored and
	 *         {@value eu.acclimatize.unison.ResponseConstant#FAILURE} if a user
	 *         with the same user name has previously been stored.
	 */
	@PostMapping(Constant.ADD_USER_MAPPING)
	public int addUser(@RequestBody UserInformation userInformation) {

		if (userInformation.existIn(userRepository)) {
			return ResponseConstant.FAILURE;
		} else {
			userRepository.save(userInformation);
			return ResponseConstant.SUCCESS;
		}

	}

}
