package eu.acclimatize.unison.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration class for the user package.
 *
 */
@Configuration
public class UserConfig {

	/**
	 * Determines whether the console is present.
	 * 
	 * @return True if the console is present and false otherwise.
	 */
	@Bean
	public Boolean consolePresent() {

		/*
		 * Removing the static System.console() call from the injected component is
		 * useful when testing. During testing, a true value is provided to the injected
		 * component; the input that would have came from the console is mocked and the
		 * branch the console is used on avoided. This is required as the system console
		 * is final and cannot be mocked using Mockito.
		 */
		return Boolean.valueOf(System.console() != null);

	}
}
