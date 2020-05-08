package eu.acclimatize.unison.user;

import java.security.Principal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.Constant;

/**
 * A controller for updating the authenticated user's password.
 *
 */
@RestController
public class UpdatePasswordController {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;

	/**
	 * Creates and instance of UpdatePasswordController.
	 * 
	 * @param passwordEncoder The encoder used to encrypt passwords.
	 * @param userRepository  The repository where user information is stored.
	 */
	public UpdatePasswordController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
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
		String encodedPassword = passwordEncoder.encode(password);
		userRepository.save(new UserInformation(userName, encodedPassword));
	}
}
