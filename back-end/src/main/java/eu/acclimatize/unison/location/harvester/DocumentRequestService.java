package eu.acclimatize.unison.location.harvester;

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
 * A service that obtains XML weather data documents from a HARMONIE-AROME endpoint
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
	 * @param documentBuilder
	 * @param template The template for a HARMONIE-AROME endpoint specified by
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
	 * @return The requested document.
	 * 
	 * @throws HarvestParseException Thrown if there was a SAX exception when parsing.
	 * @throws HarvestRequestException Thrown if there was an I/O exception when connecting to the URI.
	 * @throws DocumentNotFoundException Thrown if there was no XML document found at the URI.
	 * 
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
