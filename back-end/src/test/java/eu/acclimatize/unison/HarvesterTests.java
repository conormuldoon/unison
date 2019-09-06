package eu.acclimatize.unison;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import eu.acclimatize.unison.harvester.HarvesterService;
import eu.acclimatize.unison.location.LocationDetails;

public class HarvesterTests {

	private Calendar testFileTime(String s) {
		Instant instant = Instant.parse(s);
		Date date = Date.from(instant);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private void testParse(String fileName, Calendar cal, String timeZone)
			throws ParserConfigurationException, SAXException, IOException {

		HourlyPrecipitationRepository pr = mock(HourlyPrecipitationRepository.class);
		HourlyWeatherRepository wr = mock(HourlyWeatherRepository.class);

		Logger logger = mock(Logger.class);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'KK:mm:ss'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		
		HarvesterService hs = new HarvesterService(null, pr, wr, null, logger, dateFormat);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(getClass().getResourceAsStream(fileName));

		Optional<Document> oDoc = Optional.of(doc);

		LocationDetails location = mock(LocationDetails.class);

		when(location.requestData(any())).thenReturn(oDoc);

		Assert.assertTrue(hs.processLocation(location, cal));
		verify(pr, times(1)).saveAll(anyCollection());
		verify(wr, times(1)).saveAll(anyCollection());

	}

	// XML data obtained using the Met Eireann API
	@Test
	public void testConvertorIrl() throws ParserConfigurationException, SAXException, IOException {
		testParse("/TestIreland.xml", testFileTime("2018-05-03T14:00:00Z"), "Europe/Dublin");
	}

	// XML data obtained using the Met Eireann API
	@Test
	public void testConvertorUK() throws ParserConfigurationException, SAXException, IOException {
		testParse("/TestUK.xml", testFileTime("2019-02-28T18:00:00Z"), "Europe/Dublin");
	}

	// XML data obtained using the Norwegian Meteorological Institute API
	@Test
	public void testConvertorNor() throws ParserConfigurationException, SAXException, IOException {
		testParse("/TestNorway.xml", testFileTime("2019-04-26T18:00:00Z"), "Europe/Oslo");
	}
}
