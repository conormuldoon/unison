package eu.acclimatize.unison;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

/**
 * 
 * The application class that starts spring and configures beans.
 *
 */
@SpringBootApplication
@EnableScheduling
public class UnisonServerApplication {

	/**
	 * Starts spring application.
	 * 
	 * @param args Arguments passed to the application runner.
	 */
	public static void main(String[] args) {
		SpringApplication.run(UnisonServerApplication.class, args);
	}

	/**
	 * Creates a logger with prototype scope.
	 * 
	 * @param injectionPoint A descriptor for the constructor where the logger will
	 *                       be injected.
	 * @return The per injection point logger created.
	 */
	@Bean
	@Scope("prototype")
	public Logger logger(InjectionPoint injectionPoint) {

		return Logger.getLogger(injectionPoint.getMethodParameter().getContainingClass().getName());
	}

	/**
	 * Creates a single threaded executor for executing runnable tasks.s.
	 * 
	 * @return The executor created.
	 */
	@Bean
	public Executor executor() {
		return Executors.newSingleThreadExecutor();
	}

	@Bean
	public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
		return new FilterRegistrationBean<>(new ForwardedHeaderFilter());

	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {

		CorsConfiguration config = new CorsConfiguration();
		//config.applyPermitDefaultValues();
		
		
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedMethod(HttpMethod.OPTIONS);
		config.addAllowedMethod(HttpMethod.HEAD);
		config.addAllowedMethod(HttpMethod.GET);
		config.addAllowedMethod(HttpMethod.PUT);
		config.addAllowedMethod(HttpMethod.POST);
		config.addAllowedMethod(HttpMethod.DELETE);
		
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		config.addExposedHeader("Access-Control-Allow-Origin");
		config.addExposedHeader("Access-Control-Allow-Credentials");
		config.addExposedHeader("Access-Control-Max-Age");
		config.addExposedHeader("Access-Control-Allow-Methods");
		config.addExposedHeader("Access-Control-Allow-Origin");
		config.addExposedHeader("Access-Control-Allow-Headers");
		config.addExposedHeader("WWW-Authenticate");
		config.addExposedHeader("Authorization");
		
		

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		CorsFilter corsFilter = new CorsFilter(source);

		return new FilterRegistrationBean<>(corsFilter);
	}

}
