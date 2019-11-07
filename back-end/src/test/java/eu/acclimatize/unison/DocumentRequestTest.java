package eu.acclimatize.unison;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import eu.acclimatize.unison.harvester.DocumentRequestService;

// Tests whether the document is present in the returned optional from the DocumentRequestService.
public class DocumentRequestTest {

	@Test
	public void testHaveDocument() throws SAXException, IOException {
		DocumentBuilder documentBuilder = Mockito.mock(DocumentBuilder.class);
		Logger logger = Mockito.mock(Logger.class);

		Document d = Mockito.mock(Document.class);
		Mockito.when(documentBuilder.parse(Mockito.anyString())).thenReturn(d);
		DocumentRequestService drc = new DocumentRequestService(documentBuilder, logger);

		Optional<Document> optD = drc.documentForURI("");
		Assert.assertFalse(optD.isEmpty());
	}

	@Test
	public void testOptionalEmpty() throws SAXException, IOException {
		DocumentBuilder documentBuilder = Mockito.mock(DocumentBuilder.class);
		Logger logger = Mockito.mock(Logger.class);
		Mockito.when(documentBuilder.parse(Mockito.anyString())).thenThrow(SAXException.class);

		DocumentRequestService drc = new DocumentRequestService(documentBuilder, logger);

		Optional<Document> optD = drc.documentForURI("");
		Assert.assertTrue(optD.isEmpty());
	}
}
