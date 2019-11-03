package eu.acclimatize.unison.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.acclimatize.unison.location.ResponseConstant;

/**
 * 
 * A services that enables tasks to be executed that require user credentials.
 *
 */
@Service
public class UserService {

	private UserRepository userRepository;

	private Logger logger;

	private BCryptPasswordEncoder passwordEncoder;

	private Boolean consolePresent;

	/**
	 * Creates an instance of UserService.
	 * 
	 * @param userRepository  The repository user credentials information is stored.
	 * @param logger          Used to log events when the user consoles is not
	 *                        present, such as in testing.
	 * @param passwordEncoder Used to encrypt and match user passwords.
	 * @param consolePresent  Used to determine whether to request initial user data
	 *                        from a {@link CredentialsRequester}.
	 */
	public UserService(UserRepository userRepository, Logger logger, BCryptPasswordEncoder passwordEncoder,
			Boolean consolePresent) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.logger = logger;
		this.consolePresent = consolePresent;
	}

	@PostConstruct
	public void initialUser() {

		if (userRepository.count() == 0) {

			if (consolePresent) {

				PrintWriter pw = new PrintWriter(System.out, true);
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				CredentialsRequester requester = new CredentialsRequester(pw, br, passwordEncoder, new SecureRandom());

				try {
					UserInformation userInformation = requester.requestUserInformation();
					userRepository.save(userInformation);

					pw.close();
					br.close();
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage());
				}

			} else {
				logger.log(Level.INFO, "No active console available.");
			}

		}

	}

	/**
	 * Executes a {@link UserTask} if the user name and password match the
	 * credentials in the database.
	 * 
	 * @param userName The name of the user.
	 * @param password The user's password.
	 * @param task     The task to be executed.
	 * @return The {@link eu.acclimatize.unison.location.ResponseConstant} value for
	 *         the result of executing the task.
	 */
	@Transactional
	public int executeTask(String userName, String password, UserTask task) {
		Optional<UserInformation> oUser = userRepository.findById(userName);
		if (oUser.isPresent()) {
			UserInformation user = oUser.get();
			if (user.passwordMatches(password, passwordEncoder))
				return task.execute(user);

		}
		return ResponseConstant.INCORRECT_CREDENTIALS;

	}

}
