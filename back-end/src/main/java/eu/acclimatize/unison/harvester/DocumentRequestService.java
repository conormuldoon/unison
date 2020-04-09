package eu.acclimatize.unison.harvester;

import java.io.FileNotFoundException;
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

	private Logger logger;
	private DocumentBuilder documentBuilder;

	/**
	 * Creates an instance of DocumentRequestService.
	 * 
	 * @param documentBuilder A builder used to parse the XML document.
	 * @param logger          Used to log URIs for requested documents and
	 *                        exceptions.
	 */
	public DocumentRequestService(DocumentBuilder documentBuilder, Logger logger) {

		this.logger = logger;
		this.documentBuilder = documentBuilder;
	}

	/**
	 * Obtains an XML document from an API.
	 * 
	 * @param locName The name of the location.
	 * @param locURI The URI to obtain the document from.
	 * @return The Optional will contain the document if obtained and parsed
	 *         correctly or empty otherwise.
	 * @throws DocumentRequestException Thrown when the generated XML for the location was not found.
	 */
	public Optional<Document> documentForURI(String locName, String locURI) throws DocumentRequestException {
		logger.log(Level.INFO, () -> "Requesting data for " + locName + " from " + locURI + '.');

		try {

			Document doc = documentBuilder.parse(locURI);

			return Optional.of(doc);

		}catch(FileNotFoundException e){
			throw new DocumentRequestException("Problem obtaining document for "+ locName+". The gnerated XML was not found.");
		}
		catch (SAXException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			
		}

		return Optional.empty();
	}
}
