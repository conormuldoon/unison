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
 * Tests for the location request service.
 *
 */
public class LocationRequestTests {

	/**
	 * Tests that the document is present.
	 * 
	 * @throws SAXException             Thrown if there is a an error in parsing the
	 *                                  XML, parsing is mocked in this test and the
	 *                                  exception should not be thrown.
	 * @throws IOException              Thrown if there is an I/O error when parsing
	 *                                  the document.
	 * @throws LocationRequestException Thrown if the XML was not obtained.
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
	 * Tests that if there is a problem parsing the data, there will be no document
	 * present.
	 * 
	 * @throws SAXException             Thrown if there is a an error in parsing the
	 *                                  XML. A mock throw of this exception is
	 *                                  performed in this test, but it should be
	 *                                  caught by the system under test and should
	 *                                  not be thrown by the test method.
	 * @throws IOException              Thrown if there is an I/O error when parsing
	 *                                  the document.
	 * @throws LocationRequestException Thrown if the XML was not obtained.
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
