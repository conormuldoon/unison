package eu.acclimatize.unison;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.CUSTOM, classes = { SpatialExcludeFilter.class }))
@EnableScheduling
public class UnisonServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnisonServerApplication.class, args);
	}

	@Bean
	public DocumentBuilder documentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		return dbFactory.newDocumentBuilder();

	}

	@Bean
	@Scope("prototype")
	public Logger logger(InjectionPoint injectionPoint) {

		return Logger.getLogger(injectionPoint.getMethodParameter().getContainingClass().getName());
	}

	@Bean
	public SimpleDateFormat simpleDateFormat(@Value("${api.timezone}") String timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'KK:mm:ss'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		return dateFormat;
	}
	
	

}
