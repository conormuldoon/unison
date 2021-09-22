package eu.acclimatize.unison.jsoncontroller;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import eu.acclimatize.unison.FinderConfig;
import eu.acclimatize.unison.HourlyWeatherRepository;
import eu.acclimatize.unison.TestConstant;
import eu.acclimatize.unison.TestUtility;
import eu.acclimatize.unison.UnisonServerApplication;
import eu.acclimatize.unison.csvcontroller.CSVResponderConfig;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.result.CloudLevelResult;
import eu.acclimatize.unison.result.CloudinessResult;
import eu.acclimatize.unison.result.DewPointResult;
import eu.acclimatize.unison.result.FogResult;
import eu.acclimatize.unison.result.HumidityResult;
import eu.acclimatize.unison.result.PressureResult;
import eu.acclimatize.unison.result.TemperatureResult;
import eu.acclimatize.unison.result.WindDirectionResult;
import eu.acclimatize.unison.result.WindSpeedResult;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * Tests the JSON controllers for querying weather data.
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UnisonServerApplication.class, FinderConfig.class, CSVResponderConfig.class })

class JSONControllerTests {

	@Autowired
	private HourlyWeatherRepository hwr;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private JSONCloudinessController cloudinessController;

	@Autowired
	private JSONCloudLevelController cloudLevelController;

	@Autowired
	private JSONDewPointController dewPointController;

	@Autowired
	private JSONFogController fogController;

	@Autowired
	private JSONHumidityController humidityController;

	@Autowired
	private JSONPressureController pressureController;

	@Autowired
	private JSONTemperatureController temperatureController;

	@Autowired
	private JSONWindDirectionController windDirectionController;

	@Autowired
	private JSONWindSpeedController windSpeedController;

	@Autowired
	private UserRepository userRepository;

	private HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

	/**
	 * Adds initial data to the database.
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
	 * Test that the results returned are of type CloudinessResult.
	 */
	@Test
	void testCloudiness() {

		TestUtility.assertType(cloudinessController.cloudiness(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), CloudinessResult.class);

	}

	/**
	 * Test that the results returned are of type CloudLevelResult.
	 */
	@Test
	void testCloudLevel() {

		TestUtility.assertType(cloudLevelController.cloudLevel(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), CloudLevelResult.class);

	}

	/**
	 * Test that the results returned are of type DewPointResult.
	 */
	@Test
	void testDewPoint() {

		TestUtility.assertType(dewPointController.dewPoint(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), DewPointResult.class);

	}

	/**
	 * Test that the results returned are of type FogResult.
	 */
	@Test
	void testFog() {

		TestUtility.assertType(
				fogController.fog(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE, response),
				FogResult.class);

	}

	/**
	 * Test that the results returned are of type HumidityResult.
	 */
	@Test
	void testHumidity() {

		TestUtility.assertType(humidityController.humidity(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), HumidityResult.class);

	}

	/**
	 * Test that the results returned are of type PressureResult.
	 */
	@Test
	void testPressure() {

		TestUtility.assertType(pressureController.pressure(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), PressureResult.class);

	}

	/**
	 * Test that the results returned are of type TemperatureResult.
	 */
	@Test
	void testTemperature() {

		TestUtility.assertType(temperatureController.temperature(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), TemperatureResult.class);

	}

	/**
	 * Test that the results returned are of type WindDirectionResult.
	 */
	@Test
	void testWindDirection() {

		TestUtility.assertType(windDirectionController.windDirection(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), WindDirectionResult.class);

	}

	/**
	 * Test that the results returned are of type WindSpeedResult.
	 */
	@Test
	void testWindSpeed() {

		TestUtility.assertType(windSpeedController.windSpeed(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), WindSpeedResult.class);
	}

}
