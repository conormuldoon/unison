package eu.acclimatize.unison.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * A configuration class for the user package.
 *
 */
@Configuration
public class UserConfig {

	/**
	 * Creates an encoder for encrypting and matching user passwords.
	 * 
	 * @return The password encoder created.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Determines whether the console is present. 
	 * 
	 * @return True if the console is present and false otherwise.
	 */
	@Bean
	public Boolean consolePresent() {
		
		return Boolean.valueOf(System.console()!=null);
		
	}
}
