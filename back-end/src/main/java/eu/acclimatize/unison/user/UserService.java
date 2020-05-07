package eu.acclimatize.unison.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A service used for encoding and saving user information. The service saves
 * user information for an initial if there is no user information stored in the
 * database once the application has been initialized at start time. The
 * component uses the default.username and default.encoded properties if
 * provided. Otherwise, initial user information requested if there is a console
 * available.
 *
 */
@Component
public class UserService implements ApplicationListener<ApplicationReadyEvent> {

	private UserRepository userRepository;

	private Logger logger;

	private Boolean consolePresent;

	private String defaultUserName;

	private String defaultEncoded;

	private Executor executor;

	private PasswordEncoder passwordEncoder;

	/**
	 * Creates an instance of UserService.
	 * 
	 * @param userRepository  The repository user credentials information is stored.
	 * @param logger          Used to log events when the user consoles is not
	 *                        present, such as in testing.
	 * @param consolePresent  Used to determine whether to request initial user data
	 *                        from a {@link CredentialsRequester}.
	 * @param defaultUserName Assigned using the default.username property. If not
	 *                        null and no users stored, used in adding an initial
	 *                        user to the database.
	 * @param defaultEncoded  A bcrypt encoded password assigned using the
	 *                        default.encoded property. Used in adding an initial
	 *                        user to the database if not null and no users stored.
	 * @param executor        Used to request user credentials on a separate thread.
	 * @param passwordEncoder The encoder used to encrypt passwords.
	 */

	public UserService(UserRepository userRepository, Logger logger, Boolean consolePresent,
			@Value("${default.username:#{null}}") String defaultUserName,
			@Value("${default.encoded:#{null}}") String defaultEncoded, Executor executor,
			PasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.logger = logger;
		this.consolePresent = consolePresent;
		this.defaultUserName = defaultUserName;
		this.defaultEncoded = defaultEncoded;
		this.executor = executor;
		this.passwordEncoder = passwordEncoder;

	}
	
	/**
	 * Encrypts the user's password and saves the user information in a repository.
	 * 
	 * @param userInformation The user information to encrypt and store.
	 */
	public void encodeAndSave(UserInformation userInformation) {
		
		userInformation.encodePassword(passwordEncoder);
		userRepository.save(userInformation);
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		if (userRepository.count() == 0) {

			if (defaultUserName != null && defaultEncoded != null) {

				UserInformation userInformation = new UserInformation(defaultUserName, defaultEncoded);
				userRepository.save(userInformation);

			} else if (Boolean.TRUE.equals(consolePresent)) {
				/*
				 * During testing, a true value is provided for consolePresent; the input that
				 * would have came from the console is mocked and the branch the console is used
				 * on avoided. This is required as the system console is final and cannot be
				 * mocked using Mockito.
				 */

				executor.execute(() -> {
					PrintWriter pw = new PrintWriter(System.out, true);
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

					CredentialsRequester requester = new CredentialsRequester(pw, br, new SecureRandom());

					try {
						UserInformation userInformation = requester.requestUserInformation();
						encodeAndSave(userInformation);

						pw.close();
						br.close();
					} catch (IOException e) {
						logger.log(Level.SEVERE, e.getMessage());
					}
				});

			} else {
				logger.log(Level.INFO, "No active console available.");
			}

		}

	}
}