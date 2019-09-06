package eu.acclimatize.unison.user;

import java.io.Console;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.acclimatize.unison.location.ResponseConstant;

@Service
public class UserService {

	private UserRepository userRepository;

	private Logger logger;

	private BCryptPasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, Logger logger) {
		this.userRepository = userRepository;
		passwordEncoder = new BCryptPasswordEncoder();
		this.logger = logger;
	}

	@PostConstruct
	void initialUser() throws IOException {

		if (userRepository.count() == 0) {

			Console console = System.console();
			if (console != null) {
				UserConsole userConsole = new UserConsole(System.console(), passwordEncoder);
				UserInformation userInformation = userConsole.requestUserInformation();

				userRepository.save(userInformation);
			} else {
				logger.log(Level.INFO, "No active console available.");
			}

		}

	}

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
