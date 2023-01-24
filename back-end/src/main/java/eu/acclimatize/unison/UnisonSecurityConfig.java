package eu.acclimatize.unison;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * A security configuration class.
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class UnisonSecurityConfig {

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

	/**
	 * Creates a security filter chain to protect for CSRF vulnerabilities and to
	 * use HTTP Basic authentication for protected end-points.
	 * 
	 * @param http Used to build the filter chain.
	 * @return The built filter chain.
	 * @throws Exception Thrown if there is a CSRF or HTTP Basic issue.
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().authorizeRequests()
				.antMatchers(MappingConstant.USER).authenticated().antMatchers("/**").permitAll().and().httpBasic();

		return http.build();
	}

	/**
	 * Creates an encoder for encrypting and matching user passwords.
	 * 
	 * @return The password encoder created.
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Creates an authentication provider that uses the user details service and
	 * password encoder.
	 * 
	 * @return The authentication provider created.
	 */
	@Bean
	DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

}
