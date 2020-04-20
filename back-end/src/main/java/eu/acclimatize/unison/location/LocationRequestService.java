package eu.acclimatize.unison.location;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * A service that obtains XML weather data documents from an HARMONIE-AROME API
 * for the coordinates of locations.
 *
 */
@Service
public class LocationRequestService {

	private Logger logger;
	private DocumentBuilder documentBuilder;
	private String uri;

	/**
	 * Creates an instance of LocationRequestService.
	 * 
	 * @param documentBuilder A builder used to parse the XML document.
	 * @param logger          Used to log URIs for requested documents and
	 *                        exceptions.
	 * @param uri             The URL template for a HARMONIE-AROME API specified by
	 *                        app.uri in the application properties file.
	 */
	public LocationRequestService(DocumentBuilder documentBuilder, Logger logger,@Value("${api.uri}") String uri) {

		this.logger = logger;
		this.documentBuilder = documentBuilder;
		this.uri = uri;
	}


	/**
	 * Obtains an XML weather document document.
	 * 
	 * @param location The location used in obtaing data.
	 * @return The Optional will contain the document if obtained and parsed
	 *         correctly or empty otherwise.
	 * @throws LocationRequestException Thrown when the generated XML for the
	 *                                  location was not found.
	 */
	public Optional<Document> documentForLocation(Location location) throws LocationRequestException {

		try {

			return Optional.of(location.requestDocument(uri, documentBuilder, logger));
		} catch (SAXException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());

		}

		return Optional.empty();
	}
}
