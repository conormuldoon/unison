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

import eu.acclimatize.unison.location.LocationRequestException;
import eu.acclimatize.unison.location.LocationRequestService;

/**
 *  Tests whether the document is present in the returned optional from the DocumentRequestService.
 *
 */
public class LocationRequestTests {

	/**
	 * Tests that the document is present.
	 */
	@Test
	public void testHaveDocument() throws SAXException, IOException, LocationRequestException {
		DocumentBuilder documentBuilder = Mockito.mock(DocumentBuilder.class);
		Logger logger = Mockito.mock(Logger.class);

		Document d = Mockito.mock(Document.class);
		Mockito.when(documentBuilder.parse(Mockito.anyString())).thenReturn(d);
		LocationRequestService drc = new LocationRequestService(documentBuilder, logger, "");

		Optional<Document> optD = drc.documentForLocation(
				TestUtility.createLocation(TestConstant.LOCATION, null, TestConstant.LONGITUDE, TestConstant.LATITUDE));
		Assert.assertFalse(optD.isEmpty());
	}

	/**
	 * Tests that if there is a problem obtaining the data, there will be no
	 * document present.
	 */
	@Test
	public void testOptionalEmpty() throws SAXException, IOException, LocationRequestException {
		DocumentBuilder documentBuilder = Mockito.mock(DocumentBuilder.class);
		Logger logger = Mockito.mock(Logger.class);
		Mockito.when(documentBuilder.parse(Mockito.anyString())).thenThrow(SAXException.class);

		LocationRequestService drc = new LocationRequestService(documentBuilder, logger, "");

		Optional<Document> optD = drc.documentForLocation(
				TestUtility.createLocation(TestConstant.LOCATION, null, TestConstant.LONGITUDE, TestConstant.LATITUDE));
		Assert.assertTrue(optD.isEmpty());
	}

}
