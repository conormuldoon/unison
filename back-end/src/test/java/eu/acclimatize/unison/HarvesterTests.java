package eu.acclimatize.unison;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;
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

import eu.acclimatize.unison.harvester.DocumentNotFoundException;
import eu.acclimatize.unison.harvester.DocumentRequestService;
import eu.acclimatize.unison.harvester.HarvestParseException;
import eu.acclimatize.unison.harvester.HarvestRequestException;
import eu.acclimatize.unison.harvester.HarvesterService;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationRepository;

/**
 * Tests for the Unison data harvester and document request service.
 *
 */
public class HarvesterTests {

	private void testParse(String fileName, String timeZone) throws ParserConfigurationException, SAXException,
			IOException, HarvestParseException, HarvestRequestException, DocumentNotFoundException {

		HourlyPrecipitationRepository pr = mock(HourlyPrecipitationRepository.class);
		HourlyWeatherRepository wr = mock(HourlyWeatherRepository.class);

		Logger logger = mock(Logger.class);

		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.HARMONIE_DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(getClass().getResourceAsStream(fileName));

		DocumentBuilder mockBuilder = Mockito.mock(DocumentBuilder.class);
		Mockito.when(mockBuilder.parse(Mockito.anyString())).thenReturn(doc);

		DocumentRequestService lrs = new DocumentRequestService(mockBuilder, logger, "classpath:" + fileName);

		LocationRepository lr = Mockito.mock(LocationRepository.class);
		Location location = TestUtility.createLocation("New Location", null, 0, 0);
		Mockito.when(lr.findById(Mockito.anyString())).thenReturn(Optional.of(location));
		HarvesterService hs = new HarvesterService(lr, pr, wr, lrs, logger, dateFormat,
				new UnisonServerApplication().executor());

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
	 * @throws HarvestParseException        Thrown if the XML was not obtained.
	 * @throws HarvestRequestException
	 * @throws DocumentNotFoundException
	 */
	@Test
	public void testConvertorIrl() throws ParserConfigurationException, SAXException, IOException,
			HarvestParseException, HarvestRequestException, DocumentNotFoundException {
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
	 * @throws HarvestParseException        Thrown if the XML was not obtained.
	 * @throws HarvestRequestException
	 * @throws DocumentNotFoundException
	 */
	@Test
	public void testConvertorUK() throws ParserConfigurationException, SAXException, IOException, HarvestParseException,
			HarvestRequestException, DocumentNotFoundException {
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
	 * @throws HarvestParseException        Thrown if the XML was not obtained.
	 * @throws HarvestRequestException
	 * @throws DocumentNotFoundException
	 */
	@Test
	public void testConvertorNor() throws ParserConfigurationException, SAXException, IOException,
			HarvestParseException, HarvestRequestException, DocumentNotFoundException {
		testParse("/TestNorway.xml", "Europe/Oslo");
	}

	/**
	 * Tests that the document is present.
	 * 
	 * @throws SAXException              Thrown if there is a an error in parsing
	 *                                   the XML, parsing is mocked in this test and
	 *                                   the exception should not be thrown.
	 * @throws IOException               Thrown if there is an I/O error when
	 *                                   parsing the document.
	 * @throws HarvestParseException     Thrown if the XML was not obtained.
	 * @throws HarvestRequestException
	 * @throws DocumentNotFoundException
	 */
	@Test
	public void testHaveDocument() throws SAXException, IOException, HarvestParseException, HarvestRequestException,
			DocumentNotFoundException {
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
