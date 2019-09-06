package eu.acclimatize.unison.harvester;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Service
public class DocumentRequestService {

	Logger logger;
	DocumentBuilder documentBuilder;

	public DocumentRequestService(DocumentBuilder documentBuilder, Logger logger) {

		this.logger = logger;
		this.documentBuilder = documentBuilder;
	}

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
