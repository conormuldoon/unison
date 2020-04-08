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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.acclimatize.unison.ResponseConstant;

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

	private String defaultUserName;

	private String defaultEncoded;

	/**
	 * Creates an instance of UserService.
	 * 
	 * @param userRepository  The repository user credentials information is stored.
	 * @param logger          Used to log events when the user consoles is not
	 *                        present, such as in testing.
	 * @param passwordEncoder Used to encrypt and match user passwords.
	 * @param consolePresent  Used to determine whether to request initial user data
	 *                        from a {@link CredentialsRequester}.
	 * @param defaultUserName Assigned using the default.username property. If not
	 *                        null and no users stored, used in adding an initial
	 *                        user to the database.
	 * @param defaultEncoded  A bcrypt encoded password assigned using the
	 *                        default.encoded property. Used in adding an initial
	 *                        user to the database if not null and no users stored.
	 */
	public UserService(UserRepository userRepository, Logger logger, BCryptPasswordEncoder passwordEncoder,
			Boolean consolePresent, @Value("${default.username:#{null}}") String defaultUserName,
			@Value("${default.encoded:#{null}}") String defaultEncoded) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.logger = logger;
		this.consolePresent = consolePresent;
		this.defaultUserName = defaultUserName;
		this.defaultEncoded = defaultEncoded;
	}

	/**
	 * 
	 * If there are no users stored in the database and the default.username and
	 * default.password properties are set, they are used to add an initial user to
	 * the database. Alternatively, if the properties are not set, the console will
	 * be used to request user information if it is available and the server is
	 * running in interactive mode.
	 * 
	 */
	@PostConstruct
	public void initialUser() {

		if (userRepository.count() == 0) {

			if (defaultUserName != null && defaultEncoded != null) {

				UserInformation userInformation = new UserInformation(defaultUserName, defaultEncoded);
				userRepository.save(userInformation);

			} else if (Boolean.TRUE.equals(consolePresent)) {

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
	 * @return The {@link eu.acclimatize.unison.ResponseConstant} value for the
	 *         result of executing the task.
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
