package eu.acclimatize.unison;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import eu.acclimatize.unison.csvcontroller.CSVResponderConfig;
import eu.acclimatize.unison.jsoncontroller.JSONCloudLevelController;
import eu.acclimatize.unison.jsoncontroller.JSONCloudinessController;
import eu.acclimatize.unison.jsoncontroller.JSONDewPointController;
import eu.acclimatize.unison.jsoncontroller.JSONFogController;
import eu.acclimatize.unison.jsoncontroller.JSONHumidityController;
import eu.acclimatize.unison.jsoncontroller.JSONPressureController;
import eu.acclimatize.unison.jsoncontroller.JSONTemperatureController;
import eu.acclimatize.unison.jsoncontroller.JSONWindDirectionController;
import eu.acclimatize.unison.jsoncontroller.JSONWindSpeedController;
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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UnisonServerApplication.class, FinderConfig.class, CSVResponderConfig.class })

public class JSONControllerTests {

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

	/**
	 * Adds initial data to the database.
	 */
	@Before
	public void addWeatherData() {

		TestUtility.saveWeatherData( userRepository, locationRepository,  hwr);

	}

	/**
	 * Clears saved data from the database.
	 */
	@After
	public void deleteData() {

		TestUtility.deleteWeatherData(hwr, locationRepository, userRepository);
	}

	/**
	 * Test that the results returned are of type CloudinessResult.
	 */
	@Test
	public void testCloudiness() {

		TestUtility.assertType(
				cloudinessController.cloudiness(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE),
				CloudinessResult.class);

	}

	/**
	 * Test that the results returned are of type CloudLevelResult.
	 */
	@Test
	public void testCloudLevel() {

		TestUtility.assertType(
				cloudLevelController.cloudLevel(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE),
				CloudLevelResult.class);

	}

	/**
	 * Test that the results returned are of type DewPointResult.
	 */
	@Test
	public void testDewPoint() {

		TestUtility.assertType(
				dewPointController.dewPoint(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE),
				DewPointResult.class);

	}

	/**
	 * Test that the results returned are of type FogResult.
	 */
	@Test
	public void testFog() {

		TestUtility.assertType(fogController.fog(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE),
				FogResult.class);

	}

	/**
	 * Test that the results returned are of type HumidityResult.
	 */
	@Test
	public void testHumidity() {

		TestUtility.assertType(
				humidityController.humidity(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE),
				HumidityResult.class);

	}

	/**
	 * Test that the results returned are of type PressureResult.
	 */
	@Test
	public void testPressure() {

		TestUtility.assertType(
				pressureController.pressure(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE),
				PressureResult.class);

	}

	/**
	 * Test that the results returned are of type TemperatureResult.
	 */
	@Test
	public void testTemperature() {

		TestUtility.assertType(
				temperatureController.temperature(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE),
				TemperatureResult.class);

	}

	/**
	 * Test that the results returned are of type WindDirectionResult.
	 */
	@Test
	public void testWindDirection() {

		TestUtility.assertType(windDirectionController.windDirection(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE), WindDirectionResult.class);

	}

	/**
	 * Test that the results returned are of type WindSpeedResult.
	 */
	@Test
	public void testWindSpeed() {

		TestUtility.assertType(
				windSpeedController.windSpeed(TestConstant.LOCATION, TestConstant.FROM_DATE, TestConstant.TO_DATE),
				WindSpeedResult.class);
	}

}
