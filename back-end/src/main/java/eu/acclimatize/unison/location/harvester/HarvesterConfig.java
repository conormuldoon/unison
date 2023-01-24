package eu.acclimatize.unison.location.harvester;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.acclimatize.unison.Constant;

/**
 * A configuration class for the Unison harvester.
 */
@Configuration
public class HarvesterConfig {

	/**
	 * Creates a singleton scope document builder.
	 * 
	 * @return The document builder instance created.
	 * @throws ParserConfigurationException Will be thrown if there is a problem
	 *                                      with the parser configuration when
	 *                                      creating the document builder.
	 */
	@Bean
	DocumentBuilder documentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		return dbFactory.newDocumentBuilder();

	}

	/**
	 * Creates a DateFormat for the 'harmonie.timezone' value specified in the
	 * application properties file.
	 * 
	 * @param timeZone The time zone for the date format.
	 * @return A singleton scope date format.
	 */
	@Bean
	DateFormat harmonieDateFormat(@Value("${harmonie.timezone}") String timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.HARMONIE_DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		return dateFormat;
	}

}
