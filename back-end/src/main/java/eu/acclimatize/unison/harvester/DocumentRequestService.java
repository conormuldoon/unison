package eu.acclimatize.unison.harvester;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import eu.acclimatize.unison.location.Location;

/**
 * 
 * A service that obtains XML weather data documents from a HARMONIE-AROME API
 * for the coordinates of locations.
 *
 */
@Service
public class DocumentRequestService {

	private DocumentBuilder documentBuilder;
	private String template;

	/**
	 * Creates an instance of DocumentRequestService.
	 * 
	 * @param documentBuilder A builder used to parse the XML document.
	 * @param uri             The URL template for a HARMONIE-AROME API specified by
	 *                        app.uri in the application properties file.
	 */
	public DocumentRequestService(DocumentBuilder documentBuilder, @Value("${api.uri}") String template) {

		this.documentBuilder = documentBuilder;
		this.template = template;
	}

	/**
	 * Obtains an XML weather document document.
	 * 
	 * @param location The location used in obtaining data.
	 * @return
	 * @throws HarvestParseException
	 * @throws HarvestRequestException
	 * @throws DocumentNotFoundException
	 */
	public Document documentForLocation(Location location)
			throws HarvestRequestException, DocumentNotFoundException, HarvestParseException {

		String locationURI = location.replaceVariables(template);

		try {
			return documentBuilder.parse(locationURI);
		} catch (FileNotFoundException e) {
			throw new DocumentNotFoundException("No XML doucment was found at " + locationURI + ".");
		} catch (SAXException e) {
			throw new HarvestParseException("There was a SAX exception when parsing " + locationURI + ".");
		} catch (IOException e) {
			throw new HarvestRequestException("There was an I/O exception when connecting to " + locationURI + ".");
		}

	}
}
