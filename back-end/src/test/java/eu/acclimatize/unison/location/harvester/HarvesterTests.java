package eu.acclimatize.unison.location.harvester;

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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.HourlyWeatherRepository;
import eu.acclimatize.unison.TestUtility;
import eu.acclimatize.unison.UnisonServerApplication;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationRepository;

/**
 * Tests for the Unison data harvester and document request service.
 *
 */
@ExtendWith(SpringExtension.class)
class HarvesterTests {

	private void testParse(String fileName, String timeZone) throws ParserConfigurationException, SAXException,
			IOException, HarvestParseException, HarvestRequestException, DocumentNotFoundException {

		HourlyPrecipitationRepository pr = mock(HourlyPrecipitationRepository.class);
		HourlyWeatherRepository wr = mock(HourlyWeatherRepository.class);
		UnknownWVRepository uWVRep = mock(UnknownWVRepository.class);

		HarvestRepository harvestRepository = new HarvestRepository(pr, wr, uWVRep);

		Logger logger = mock(Logger.class);

		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.HARMONIE_DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(getClass().getResourceAsStream(fileName));

		Location location = TestUtility.createLocation("New Location", null, 0, 0);

		DocumentRequestService mockDRS = Mockito.mock(DocumentRequestService.class);
		Mockito.when(mockDRS.documentForLocation(location)).thenReturn(doc);

		LocationRepository lr = Mockito.mock(LocationRepository.class);

		Mockito.when(lr.findById(Mockito.anyString())).thenReturn(Optional.of(location));
		HarvesterService hs = new HarvesterService(lr, mockDRS, logger, dateFormat,
				new UnisonServerApplication().executor(), harvestRepository);

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
	void testConvertorIrl() throws ParserConfigurationException, SAXException, IOException, HarvestParseException,
			HarvestRequestException, DocumentNotFoundException {
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
	void testConvertorUK() throws ParserConfigurationException, SAXException, IOException, HarvestParseException,
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
	void testConvertorNor() throws ParserConfigurationException, SAXException, IOException, HarvestParseException,
			HarvestRequestException, DocumentNotFoundException {
		testParse("/TestNorway.xml", "Europe/Oslo");
	}

	
}
