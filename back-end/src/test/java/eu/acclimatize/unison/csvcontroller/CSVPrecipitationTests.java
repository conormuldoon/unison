package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.acclimatize.unison.FinderConfig;
import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.PrecipitationValue;
import eu.acclimatize.unison.TestConstant;
import eu.acclimatize.unison.TestUtility;
import eu.acclimatize.unison.UnisonServerApplication;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * Tests the CSV controller for querying precipitation data.
 *
 */
@SpringBootTest(classes = { UnisonServerApplication.class, FinderConfig.class, CSVResponderConfig.class })

class CSVPrecipitationTests {

	@Autowired
	private CSVPrecipitationController csvPrecipitationController;

	@Autowired
	private HourlyPrecipitationRepository hpr;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	private HttpServletResponse response;
	private StringWriter sw;

	CSVPrecipitationTests() throws IOException {

		response = Mockito.mock(HttpServletResponse.class);
		sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		Mockito.when(response.getWriter()).thenReturn(pw);

	}

	/**
	 * Clears saved data from the database.
	 */
	@AfterEach
	void deleteData() {

		TestUtility.deletePrecipitationData(hpr, locationRepository, userRepository);
	}

	/**
	 * Tests the number of CSV columns for precipitation data when min and max
	 * values are not null.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVPrecipMultiple() throws IOException {
		TestUtility.savePrecipitationData(userRepository, locationRepository, hpr);
		csvPrecipitationController.precipitation(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE,
				response);
		TestUtility.assertLength(sw, 4);

	}

	/**
	 * Tests the values of -1.0 are provided for min and max values for
	 * precipitation data when the min and max values are null.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer from the HTTP servlet response.
	 */
	@Test
	void testCSVPrecipSingle() throws IOException {
		TestUtility.savePrecipitationData(userRepository, locationRepository, new PrecipitationValue(0d, null, null),
				hpr);
		csvPrecipitationController.precipitation(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE,
				response);

		int len = 4;

		String[] str = sw.toString().split("\n");
		String[] st0 = str[0].split(",");
		String[] st1 = str[1].split(",");
		Assertions.assertEquals(len, st1.length);
		Assertions.assertEquals(len, st0.length);
		Assertions.assertEquals("-1.0", st1[2]);
		Assertions.assertEquals("-1.0", st1[3]);
	}

}
