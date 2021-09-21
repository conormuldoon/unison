package eu.acclimatize.unison.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import eu.acclimatize.unison.Constant;

/**
 * 
 * A service for replacing or updating user information.
 *
 */
@Service
public class UserService {

	private UserRepository userRepository;

	/**
	 * Creates an instance of UserService.
	 * 
	 * @param userRepository The repository where user infromation is stored.
	 */
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * The user repository will be updated if the authenticated added the original
	 * user information.
	 * 
	 * @param current The user information stored in the repository.
	 * @param updated The new user information with the same user name.
	 */
	@PreAuthorize(Constant.REPLACE_ITEM)
	public void replace(UserInformation current, UserInformation updated) {

		userRepository.save(updated);
		
	}
}
