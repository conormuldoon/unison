package eu.acclimatize.unison.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A service that loads user details for authentication.
 *
 */
@Service
public class UnisonUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	/**
	 * Creates an instance of UnisonUserDetailsService.
	 * 
	 * @param userRepository The repository user credentials information are stored
	 *                       in.
	 */

	public UnisonUserDetailsService(UserRepository userRepository) {

		this.userRepository = userRepository;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInformation> uiOpt = userRepository.findById(username);
		if (uiOpt.isPresent()) {
			return uiOpt.get().buildUser();
		} else {
			throw new UsernameNotFoundException(username);
		}

	}

}
