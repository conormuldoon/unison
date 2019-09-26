package eu.acclimatize.unison.harvester;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * A service that obtains XML documents from an API.
 *
 */
@Service
public class DocumentRequestService {

	Logger logger;
	DocumentBuilder documentBuilder;

	/**
	 * Creates an instance of DocumentRequestService.
	 * 
	 * @param documentBuilder A builder used to parse the XML document.
	 * @param logger Used to log URIs for requested documents.
	 */
	public DocumentRequestService(DocumentBuilder documentBuilder, Logger logger) {

		this.logger = logger;
		this.documentBuilder = documentBuilder;
	}

	/**
	 * Obtains an XML document from an API.
	 * 
	 * @param locURI The URI to obtain the document from.
	 * @return The Optional will contain the document if obtained and parsed correctly or empty otherwise.
	 */
	public Optional<Document> documentForURI(String locURI) {
		logger.log(Level.INFO, "Requesting data for " + locURI + '.');

		try {

			Document doc = documentBuilder.parse(locURI);

			return Optional.of(doc);

		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
