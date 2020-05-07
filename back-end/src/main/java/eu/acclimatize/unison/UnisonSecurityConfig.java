package eu.acclimatize.unison;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A security configuration class that restricts access to the end points for
 * adding locations and adding users, for deleting locations, and for updating
 * passwords.
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UnisonSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;

	/**
	 * Creates an instance of UnisonSecurityConfig.
	 * 
	 * @param userDetailsService The user details service used in authenticating
	 *                           users.
	 */
	public UnisonSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers(Constant.ADD_LOCATION_MAPPING, Constant.DELETE_LOCATION_MAPPING, Constant.ADD_USER_MAPPING,
						Constant.UPDATE_PASSWORD_MAPPING)
				.authenticated().antMatchers("/**").permitAll().and().httpBasic();
	}
	
	/**
	 * Creates an encoder for encrypting and matching user passwords.
	 * 
	 * @return The password encoder created.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(authProvider);
	}

}
