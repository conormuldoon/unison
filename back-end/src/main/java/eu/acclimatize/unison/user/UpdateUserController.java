package eu.acclimatize.unison.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.MappingConstant;

/**
 * A controller for updating the authenticated user.
 *
 */
@RestController
public class UpdateUserController {

	private final static String USER_NAME = "userName";

	private UserService userService;

	/**
	 * Creates and instance of UpdateUserController.
	 * 
	 * @param userService The service used to update the user.
	 */
	public UpdateUserController(UserService userService) {

		this.userService = userService;
	}

	/**
	 * Updates the authenticated user. The user can only update their own
	 * information using this method.
	 * 
	 * @param principal The user name for the update.
	 * @param password  The updated user information.
	 */
	@PutMapping(MappingConstant.USER + "/{" + USER_NAME + "}")
	public void updateUser(@PathVariable(USER_NAME) String userName, @RequestBody UserInformation userInformation) {

		userService.update(userName, userInformation);

	}
}
