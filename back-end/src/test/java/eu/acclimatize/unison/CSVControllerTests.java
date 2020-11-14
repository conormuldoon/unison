package eu.acclimatize.unison;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import eu.acclimatize.unison.csvcontroller.CSVCloudLevelController;
import eu.acclimatize.unison.csvcontroller.CSVCloudinessController;
import eu.acclimatize.unison.csvcontroller.CSVDewPointController;
import eu.acclimatize.unison.csvcontroller.CSVFogController;
import eu.acclimatize.unison.csvcontroller.CSVHumidityController;
import eu.acclimatize.unison.csvcontroller.CSVPressureController;
import eu.acclimatize.unison.csvcontroller.CSVResponderConfig;
import eu.acclimatize.unison.csvcontroller.CSVTemperatureController;
import eu.acclimatize.unison.csvcontroller.CSVWindDirectionController;
import eu.acclimatize.unison.csvcontroller.CSVWindSpeedController;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * Tests the CSV controllers for querying weather data.
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UnisonServerApplication.class, FinderConfig.class, CSVResponderConfig.class })
public class CSVControllerTests {

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

	public CSVControllerTests() throws IOException {

		response = Mockito.mock(HttpServletResponse.class);
		sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		Mockito.when(response.getWriter()).thenReturn(pw);

	}

	/**
	 * Add initial data to the database.
	 */
	@BeforeEach
	public void addWeatherData() {

		TestUtility.saveWeatherData(userRepository, locationRepository, hwr);

	}

	/**
	 * Clears saved data from the database.
	 */
	@AfterEach
	public void deleteData() {

		TestUtility.deleteWeatherData(hwr, locationRepository, userRepository);
	}

	/**
	 * Tests the number of CSV columns for cloudiness data.
	 * 
	 * @throws IOException Thrown if there is an I/O error in obtaining a writer
	 *                     from the HTTP servlet response.
	 */
	@Test
	public void testCSVCloudiness() throws IOException {

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
	public void testCSVCloudLevel() throws IOException {

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
	public void testCSVDewPoint() throws IOException {

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
	public void testCSVFog() throws IOException {

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
	public void testCSVHumidity() throws IOException {

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
	public void testCSVPressure() throws IOException {

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
	public void testCSVTemperature() throws IOException {

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
	public void testCSVWindDirection() throws IOException {

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
	public void testCSVWindSpeed() throws IOException {

		csvWindSpeedController.windSpeed(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE, response);
		TestUtility.assertLength(sw, 4);

	}

}
