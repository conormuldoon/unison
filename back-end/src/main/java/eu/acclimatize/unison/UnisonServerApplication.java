package eu.acclimatize.unison;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

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
	
	
}
