package eu.acclimatize.unison;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import eu.acclimatize.unison.location.HarvesterService;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationRequestException;
import eu.acclimatize.unison.location.DocumentRequestService;

/**
 * Tests for the Unison data harvester and document request service.
 *
 */
public class HarvesterTests {

	private void testParse(String fileName, String timeZone)
			throws ParserConfigurationException, SAXException, IOException, LocationRequestException {

		HourlyPrecipitationRepository pr = mock(HourlyPrecipitationRepository.class);
		HourlyWeatherRepository wr = mock(HourlyWeatherRepository.class);

		Logger logger = mock(Logger.class);

		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.HARMONIE_DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		DocumentRequestService lrs = new DocumentRequestService(dBuilder, null, "");

		HarvesterService hs = new HarvesterService(null, pr, wr, lrs, logger, dateFormat,
				new UnisonServerApplication().executor());

		Document doc = dBuilder.parse(getClass().getResourceAsStream(fileName));

		Location location = mock(Location.class);

		when(location.requestDocument(any(), any(), any())).thenReturn(doc);

		hs.fetchAndStore(location);

		verify(pr, times(1)).saveAll(anyCollection());
		verify(wr, times(1)).saveAll(anyCollection());

	}

	/**
	 * Tests that parsed documents for Ireland are saved. The XML data was obtained
	 * using the Met Eireann API.
	 * 
	 * @throws ParserConfigurationException Thrown if there is a problem the the
	 *                                      parser configuration.
	 * @throws SAXException                 Thrown if there is a an error in parsing
	 *                                      the XML.
	 * 
	 * @throws IOException                  Thrown if there is an I/O error when
	 *                                      parsing the document.
	 * @throws LocationRequestException     Thrown if the XML was not obtained.
	 */
	@Test
	public void testConvertorIrl()
			throws ParserConfigurationException, SAXException, IOException, LocationRequestException {
		testParse("/TestIreland.xml", "Europe/Dublin");
	}

	/**
	 * Tests that parsed documents for the U.K. are saved. The XML data was obtained
	 * using the Met Eireann API.
	 * 
	 * @throws ParserConfigurationException Thrown if there is a problem the the
	 *                                      parser configuration.
	 * @throws SAXException                 Thrown if there is a an error in parsing
	 *                                      the XML.
	 * 
	 * @throws IOException                  Thrown if there is an I/O error when
	 *                                      parsing the document.
	 * @throws LocationRequestException     Thrown if the XML was not obtained.
	 */
	@Test
	public void testConvertorUK()
			throws ParserConfigurationException, SAXException, IOException, LocationRequestException {
		testParse("/TestUK.xml", "Europe/Dublin");
	}

	/**
	 * Tests that parsed documents for Norway are saved. The XML data was obtained
	 * using the Norwegian Meteorological Institute API.
	 * 
	 * @throws ParserConfigurationException Thrown if there is a problem the the
	 *                                      parser configuration.
	 * @throws SAXException                 Thrown if there is a an error in parsing
	 *                                      the XML.
	 * 
	 * @throws IOException                  Thrown if there is an I/O error when
	 *                                      parsing the document.
	 * @throws LocationRequestException     Thrown if the XML was not obtained.
	 */
	@Test
	public void testConvertorNor()
			throws ParserConfigurationException, SAXException, IOException, LocationRequestException {
		testParse("/TestNorway.xml", "Europe/Oslo");
	}

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
		DocumentRequestService drc = new DocumentRequestService(documentBuilder, logger, "");

		Document document = drc.documentForLocation(
				TestUtility.createLocation(TestConstant.LOCATION, null, TestConstant.LONGITUDE, TestConstant.LATITUDE));
		Assert.assertNotNull(document);
	}
}
