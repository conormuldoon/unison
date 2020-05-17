package eu.acclimatize.unison.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.OwnedItem;

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
	@PreAuthorize(Constant.OWNED_ITEM)
	public void replace(OwnedItem ownedItem, UserInformation updated) {
		userRepository.save(updated);
	}
}
