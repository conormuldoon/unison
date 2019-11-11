package eu.acclimatize.unison.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.ResponseConstant;

/**
 * 
 * A task used for storing user credentials.
 *
 */
public class CredentialsTask implements UserTask {

	private BCryptPasswordEncoder passwordEncoder;
	private String userName;
	private String password;
	private UserRepository userRepository;

	/**
	 * Creates an instance of CredentialsTask.
	 * 
	 * @param userName The name of the user.
	 * @param password The password of the user.
	 * @param passwordEncoder Used to encrypt the user's password.
	 * @param userRepository The repository that stores user information.
	 */
	public CredentialsTask(String userName, String password, BCryptPasswordEncoder passwordEncoder,
			UserRepository userRepository) {

		this.userName = userName;
		this.password = password;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	@Override
	public int execute(UserInformation user) {
		String encodedPassword = passwordEncoder.encode(password);
		UserInformation userInformation = new UserInformation(userName, encodedPassword);
		userRepository.save(userInformation);
		return ResponseConstant.SUCCESS;

	}

}
