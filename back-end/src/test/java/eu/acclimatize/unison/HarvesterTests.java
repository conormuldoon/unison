package eu.acclimatize.unison;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import eu.acclimatize.unison.harvester.DocumentRequestException;
import eu.acclimatize.unison.harvester.HarvesterService;
import eu.acclimatize.unison.location.LocationDetails;

public class HarvesterTests {

	private void testParse(String fileName, String timeZone)
			throws ParserConfigurationException, SAXException, IOException, DocumentRequestException {

		Executor executor=mock(Executor.class);

		Logger logger = mock(Logger.class);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'KK:mm:ss'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

		HarvesterService hs = new HarvesterService(null, null, null, null, logger, dateFormat,
				executor);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(getClass().getResourceAsStream(fileName));

		Optional<Document> oDoc = Optional.of(doc);

		LocationDetails location = mock(LocationDetails.class);

		when(location.requestData(any())).thenReturn(oDoc);

		Assert.assertTrue(hs.processLocation(location));
		verify(executor, times(1)).execute(any());

	}

	// XML data obtained using the Met Eireann API
	@Test
	public void testConvertorIrl()
			throws ParserConfigurationException, SAXException, IOException, DocumentRequestException {
		testParse("/TestIreland.xml", "Europe/Dublin");
	}

	// XML data obtained using the Met Eireann API
	@Test
	public void testConvertorUK()
			throws ParserConfigurationException, SAXException, IOException, DocumentRequestException {
		testParse("/TestUK.xml", "Europe/Dublin");
	}

	// XML data obtained using the Norwegian Meteorological Institute API
	@Test
	public void testConvertorNor()
			throws ParserConfigurationException, SAXException, IOException, DocumentRequestException {
		testParse("/TestNorway.xml", "Europe/Oslo");
	}
}
