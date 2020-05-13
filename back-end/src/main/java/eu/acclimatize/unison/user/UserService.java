package eu.acclimatize.unison.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PreAuthorize("#userName == authentication.name")
	public void update(String userName, UserInformation userInformation) {
		userRepository.save(userInformation);
	}

}
