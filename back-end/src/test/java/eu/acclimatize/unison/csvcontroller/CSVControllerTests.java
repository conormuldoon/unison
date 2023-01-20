package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.acclimatize.unison.FinderConfig;
import eu.acclimatize.unison.HourlyWeatherRepository;
import eu.acclimatize.unison.TestConstant;
import eu.acclimatize.unison.TestUtility;
import eu.acclimatize.unison.UnisonServerApplication;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * Tests the CSV controllers for querying weather data.
 *
 */
@SpringBootTest(classes = { UnisonServerApplication.class, FinderConfig.class, CSVResponderConfig.class })
class CSVControllerTests {

	@Autowired
	private CSVCloudinessController csvCloudinessController;

	@Autowired
	private CSVCloudLevelController csvCloudLevelController;

	@Autowired
	private CSVDewPointController csvDewPointController;

	@Autowired
	private CSVFogController csvFogController;

	@Autowired
	private CSVHumidityController csvHumidityController;

	@Autowired
	private CSVPressureController csvPressureController;

	@Autowired
	private CSVTemperatureController csvTemperatureController;

	@Autowired
	private CSVWindDirectionController csvWindDirectionController;

	@Autowired
	private CSVWindSpeedController csvWindSpeedController;

	@Autowired
	private HourlyWeatherRepository hwr;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	private HttpServletResponse response;

	private StringWriter sw;

	CSVControllerTests() throws IOException {

		response = Mockito.mock(HttpServletResponse.class);
		sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		Mockito.when(response.getWriter()).thenReturn(pw);

	}

	/**
	 * Add initial data to the database.
	 */
	@BeforeEach
	void addWeatherData() {

		TestUtility.saveWeatherData(userRepository, locationRepository, hwr);

	}

	/**
	 * Clears saved data from the database.
	 */
	@AfterEach
	void deleteData() {

		TestUtility.deleteWeatherData(hwr, locationRepository, userRepository);
	}

	/**
	 * Tests the number of CSV columns for cloudiness data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVCloudiness() throws IOException {

		csvCloudinessController.cloudiness(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE,
				response);
		TestUtility.assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for cloud level data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVCloudLevel() throws IOException {

		csvCloudLevelController.cloudLevel(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE,
				response);
		TestUtility.assertLength(sw, 4);
	}

	/**
	 * Tests the number of CSV columns for dew point data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVDewPoint() throws IOException {

		csvDewPointController.dewPoint(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE, response);
		TestUtility.assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for fog data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVFog() throws IOException {

		csvFogController.fog(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE, response);
		TestUtility.assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for humidity data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVHumidity() throws IOException {

		csvHumidityController.humidity(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE, response);
		TestUtility.assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for pressure data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVPressure() throws IOException {

		csvPressureController.pressure(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE, response);
		TestUtility.assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for temperature data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVTemperature() throws IOException {

		csvTemperatureController.temperature(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE,
				response);
		TestUtility.assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for wind direction data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVWindDirection() throws IOException {

		csvWindDirectionController.windDirection(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE,
				response);
		TestUtility.assertLength(sw, 3);
	}

	/**
	 * Tests the number of CSV columns for wind speed data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	void testCSVWindSpeed() throws IOException {

		csvWindSpeedController.windSpeed(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE, response);
		TestUtility.assertLength(sw, 4);

	}

}
