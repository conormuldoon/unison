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
import org.springframework.core.MethodParameter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import eu.acclimatize.unison.result.PrecipitationResult;

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
	 * @throws NoMethodParameterException
	 */
	@Bean
	@Scope("prototype")
	Logger logger(InjectionPoint injectionPoint) throws NoMethodParameterException {

		MethodParameter mp = injectionPoint.getMethodParameter();
		if (mp == null) {
			throw new NoMethodParameterException(
					"A null method parameter was received for the injection point for the logger.");
		}
		return Logger.getLogger(mp.getContainingClass().getName());

	}

	/**
	 * Creates a single threaded executor for executing runnable tasks.
	 * 
	 * @return The executor created.
	 */
	@Bean
	Executor executor() {
		return Executors.newSingleThreadExecutor();
	}

	/**
	 * Used to forward headers. This is required when the application is operating
	 * behind a proxy and using HAL.
	 * 
	 * @return A bean that registers a forward header filter in the servlet
	 *         container.
	 */
	@Bean
	FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
		return new FilterRegistrationBean<>(new ForwardedHeaderFilter());

	}

	/**
	 * Used to alter caching header, which conditional on the date range of queries.
	 * 
	 * @return A bean for altering caching headers.
	 */
	@Bean
	CacheSupport cacheSupport() {
		return new CacheSupport();
	}

	/**
	 * Use to add strong ETag headers.
	 * 
	 * @return A bean that computes ETag values for responses.
	 */
	@Bean
	ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

	/**
	 * Used to ensure responses contain only single value or ternary precipitation
	 * results.
	 * 
	 * @param precipitationFinder The finder used to obtain precipitation results.
	 * @return A bean for filtering precipitation results.
	 */
	@Bean
	PrecipitationResultFilter resultFilter(ItemListFinder<PrecipitationResult> precipitationFinder) {
		return new PrecipitationResultFilter(precipitationFinder);
	}

	/**
	 * A bean for creating builders used in creating links.
	 *
	 * @return The builder created.
	 */
	@Bean
	BaseURIBuilder builder() {
		return new BaseURIBuilder();
	}

}
