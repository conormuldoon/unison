package eu.acclimatize.unison.harvester;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration class for the data harvester.
 *
 */
@Configuration
public class HarvesterConfig {

	/**
	 * 
	 * @return A single thread executor for executing runnable tasks.
	 */
	@Bean
	public Executor executor() {
		return  Executors.newSingleThreadExecutor();
	}
}
