package eu.acclimatize.unison;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
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
	 * Creates a singleton scope document builder.
	 * 
	 * @return The document builder instance created.
	 * @throws ParserConfigurationException Will be thrown if there is a problem
	 *                                      with the parser configuration when
	 *                                      creating the document builder.
	 */
	@Bean
	public DocumentBuilder documentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		return dbFactory.newDocumentBuilder();

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
	 * Creates a SimpleDateFormat for the 'api.timezone' value specified in the
	 * application properties file.
	 * 
	 * @param timeZone The time zone for the date format.
	 * @return A singleton scope date format.
	 */
	@Bean
	public SimpleDateFormat simpleDateFormat(@Value("${api.timezone}") String timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.HARMONIE_DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		return dateFormat;
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
