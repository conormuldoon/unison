package eu.acclimatize.unison.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * The user repository will be updated if the authenticated added the original
	 * user information.
	 * 
	 * @param current
	 * @param updated
	 */
	@PreAuthorize("#current.hasOwner(authentication.name)")
	public void replace(UserInformation current, UserInformation updated) {
		userRepository.save(updated);
	}
}
