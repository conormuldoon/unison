package eu.acclimatize.unison;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.HandlerMapping;

import eu.acclimatize.unison.csvcontroller.CSVCloudLevelController;
import eu.acclimatize.unison.csvcontroller.CSVCloudinessController;
import eu.acclimatize.unison.csvcontroller.CSVDewPointController;
import eu.acclimatize.unison.csvcontroller.CSVFogController;
import eu.acclimatize.unison.csvcontroller.CSVHumidityController;
import eu.acclimatize.unison.csvcontroller.CSVPrecipitationController;
import eu.acclimatize.unison.csvcontroller.CSVPressureController;
import eu.acclimatize.unison.csvcontroller.CSVResponder;
import eu.acclimatize.unison.csvcontroller.CSVResponderConfig;
import eu.acclimatize.unison.csvcontroller.CSVTemperatureController;
import eu.acclimatize.unison.csvcontroller.CSVWindDirectionController;
import eu.acclimatize.unison.csvcontroller.CSVWindSpeedController;
import eu.acclimatize.unison.jsoncontroller.CloudLevelController;
import eu.acclimatize.unison.jsoncontroller.CloudinessController;
import eu.acclimatize.unison.jsoncontroller.DewPointController;
import eu.acclimatize.unison.jsoncontroller.FogController;
import eu.acclimatize.unison.jsoncontroller.HumidityController;
import eu.acclimatize.unison.jsoncontroller.PrecipitationController;
import eu.acclimatize.unison.jsoncontroller.PressureController;
import eu.acclimatize.unison.jsoncontroller.TemperatureController;
import eu.acclimatize.unison.jsoncontroller.WindDirectionController;
import eu.acclimatize.unison.jsoncontroller.WindSpeedController;
import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.result.CloudLevelResult;
import eu.acclimatize.unison.result.CloudinessResult;
import eu.acclimatize.unison.result.DewPointResult;
import eu.acclimatize.unison.result.FogResult;
import eu.acclimatize.unison.result.HumidityResult;
import eu.acclimatize.unison.result.PrecipitationResult;
import eu.acclimatize.unison.result.PressureResult;
import eu.acclimatize.unison.result.TemperatureResult;
import eu.acclimatize.unison.result.WindDirectionResult;
import eu.acclimatize.unison.result.WindSpeedResult;
import eu.acclimatize.unison.user.UserInformation;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * Tests the controllers (CSV and JSON) for querying Unison data.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UnisonServerApplication.class, FinderConfig.class, CSVResponderConfig.class })

public class ControllerTests {

	private HttpServletResponse response;

	@Autowired
	private HourlyWeatherRepository hwr;

	@Autowired
	private HourlyPrecipitationRepository hpr;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ItemListFinder cloudinessFinder, cloudLevelFinder, dewPointFinder, fogFinder, humidityFinder,
			precipitationFinder, pressureFinder, temperatureFinder, windDirectionFinder, windSpeedFinder;

	@Autowired
	private CSVResponder cloudinessResponder, cloudLevelResponder, dewPointResponder, fogResponder, humidityResponder,
			precipitationResponder, pressureResponder, temperatureResponder, windDirectionResponder, windSpeedResponder;

	@Autowired
	private MappingForwardController forwardController;

	@Autowired
	private UserRepository userRepository;

	private Date fromDate, toDate;

	private StringWriter sw;
	private Location location;
	private ItemKey ik;

	public ControllerTests() throws IOException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		fromDate = cal.getTime();
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 7);
		toDate = cal.getTime();
		response = Mockito.mock(HttpServletResponse.class);
		sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		Mockito.when(response.getWriter()).thenReturn(pw);

	}

	/**
	 * Add initial data to the database.
	 */
	@Before
	public void addWeatherData() {

		UserInformation userInformation = new UserInformation(TestConstant.USERNAME, TestConstant.PASSWORD);
		userRepository.save(userInformation);
		location = new Location(TestConstant.LOCATION, userInformation,
				new GeometryFactory().createPoint(new Coordinate(TestConstant.LONGITUDE, TestConstant.LATITUDE)));
		locationRepository.save(location);
		ik = new ItemKey(fromDate, location);

		WindDirection wd = new WindDirection(0, "Lett bris");
		WindSpeed ws = new WindSpeed(0, 0, "W");
		Cloud cloud = new Cloud(0d, 0d, 0d);
		WeatherValue weatherValue = new WeatherValue(0d, wd, ws, 0d, 0d, 0d, cloud, 0d, 0d);

		HourlyWeather weatherData = new HourlyWeather(ik, weatherValue);

		hwr.save(weatherData);

	}

	/**
	 * Clears saved data from the database.
	 */
	@After
	public void deleteData() {

		hwr.deleteAll();
		hpr.deleteAll();
		locationRepository.deleteAll();
		userRepository.deleteAll();
	}

	/**
	 * Test that the results returned are of type CloudinessResult.
	 */
	@Test
	public void testCloudiness() {

		CloudinessController cloudinessController = new CloudinessController(cloudinessFinder);

		assertType(cloudinessController.cloudiness(TestConstant.LOCATION, fromDate, toDate), CloudinessResult.class);

	}

	/**
	 * Test that the results returned are of type CloudLevelResult.
	 */
	@Test
	public void testCloudLevel() {
		CloudLevelController cloudLevelController = new CloudLevelController(cloudLevelFinder);
		assertType(cloudLevelController.cloudLevel(TestConstant.LOCATION, fromDate, toDate), CloudLevelResult.class);

	}

	/**
	 * Test that the results returned are of type DewPointResult.
	 */
	@Test
	public void testDewPoint() {
		DewPointController dewPointController = new DewPointController(dewPointFinder);
		assertType(dewPointController.dewPoint(TestConstant.LOCATION, fromDate, toDate), DewPointResult.class);

	}

	/**
	 * Test that the results returned are of type FogResult.
	 */
	@Test
	public void testFog() {

		FogController fogController = new FogController(fogFinder);
		assertType(fogController.fog(TestConstant.LOCATION, fromDate, toDate), FogResult.class);

	}

	/**
	 * Test that the results returned are of type HumidityResult.
	 */
	@Test
	public void testHumidity() {
		HumidityController humidityController = new HumidityController(humidityFinder);
		assertType(humidityController.humidity(TestConstant.LOCATION, fromDate, toDate), HumidityResult.class);

	}

	/**
	 * Test that the results returned are of type PrecipitationResult.
	 */
	@Test
	public void testPreciptiation() {
		PrecipitationValue pv = new PrecipitationValue(0d, 0d, 0d);
		HourlyPrecipitation precip = new HourlyPrecipitation(ik, pv);
		hpr.save(precip);

		PrecipitationController precipitationController = new PrecipitationController(precipitationFinder);
		assertType(precipitationController.precipitation(TestConstant.LOCATION, fromDate, toDate),
				PrecipitationResult.class);

	}

	/**
	 * Test that the results returned are of type PressureResult.
	 */
	@Test
	public void testPressure() {

		PressureController pressureController = new PressureController(pressureFinder);
		assertType(pressureController.pressure(TestConstant.LOCATION, fromDate, toDate), PressureResult.class);

	}

	/**
	 * Test that the results returned are of type TemperatureResult.
	 */
	@Test
	public void testTemperature() {

		TemperatureController temperatureController = new TemperatureController(temperatureFinder);
		assertType(temperatureController.temperature(TestConstant.LOCATION, fromDate, toDate), TemperatureResult.class);

	}

	/**
	 * Test that the results returned are of type WindDirectionResult.
	 */
	@Test
	public void testWindDirection() {

		WindDirectionController windDirectionController = new WindDirectionController(windDirectionFinder);
		assertType(windDirectionController.windDirection(TestConstant.LOCATION, fromDate, toDate),
				WindDirectionResult.class);

	}

	/**
	 * Test that the results returned are of type WindSpeedResult.
	 */
	@Test
	public void testWindSpeed() {

		WindSpeedController windSpeedController = new WindSpeedController(windSpeedFinder);
		assertType(windSpeedController.windSpeed(TestConstant.LOCATION, fromDate, toDate), WindSpeedResult.class);
	}

	/**
	 * Tests that requests to /apiacc/ are prepended with forward.
	 */
	@Test
	public void testMappingForward() {

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE))
				.thenReturn("/apiacc/location");
		Assert.assertEquals("forward:/location", forwardController.forward(request));
	}

	private void assertLength(StringWriter sw, int len) {
		String[] str = sw.toString().split("\n");
		String[] st0 = str[0].split(",");
		String[] st1 = str[1].split(",");
		Assert.assertEquals(len, st1.length);
		Assert.assertEquals(len, st0.length);
	}

	/**
	 * Tests the number of CSV columns for cloudiness data.
	 */
	@Test
	public void testCSVCloudiness() throws IOException {

		CSVCloudinessController csvCloudinessController = new CSVCloudinessController(cloudinessResponder);
		csvCloudinessController.cloudiness(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for cloud level data.
	 */
	@Test
	public void testCSVCloudLevel() throws IOException {

		CSVCloudLevelController csvCloudLevelController = new CSVCloudLevelController(cloudLevelResponder);
		csvCloudLevelController.cloudLevel(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 4);
	}

	/**
	 * Tests the number of CSV columns for dew point data.
	 */
	@Test
	public void testCSVDewPoint() throws IOException {

		CSVDewPointController csvDewPointController = new CSVDewPointController(dewPointResponder);
		csvDewPointController.dewPoint(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for fog data.
	 */
	@Test
	public void testCSVFog() throws IOException {

		CSVFogController csvFogController = new CSVFogController(fogResponder);
		csvFogController.fog(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for humidity data.
	 */
	@Test
	public void testCSVHumidity() throws IOException {

		CSVHumidityController csvHumidityController = new CSVHumidityController(humidityResponder);
		csvHumidityController.humidity(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for precipitation data when min and max
	 * values are not null.
	 */
	@Test
	public void testCSVPrecipMultiple() throws IOException {
		PrecipitationValue pv = new PrecipitationValue(0d, 0d, 0d);
		HourlyPrecipitation precip = new HourlyPrecipitation(ik, pv);
		hpr.save(precip);
		CSVPrecipitationController csvPrecipitationController = new CSVPrecipitationController(precipitationResponder);
		csvPrecipitationController.precipitation(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 4);

	}

	/**
	 * Tests the values of -1.0 are provided for min and max values for
	 * precipitation data when the min and max values are null.
	 */
	@Test
	public void testCSVPrecipSingle() throws IOException {
		PrecipitationValue pv = new PrecipitationValue(0d, null, null);
		HourlyPrecipitation precip = new HourlyPrecipitation(ik, pv);
		hpr.save(precip);
		CSVPrecipitationController csvPrecipitationController = new CSVPrecipitationController(precipitationResponder);
		csvPrecipitationController.precipitation(TestConstant.LOCATION, fromDate, toDate, response);
		int len = 4;

		String[] str = sw.toString().split("\n");
		String[] st0 = str[0].split(",");
		String[] st1 = str[1].split(",");
		Assert.assertEquals(len, st1.length);
		Assert.assertEquals(len, st0.length);
		Assert.assertEquals("-1.0", st1[2]);
		Assert.assertEquals("-1.0", st1[3]);
	}

	/**
	 * Tests the number of CSV columns for pressure data.
	 */
	@Test
	public void testCSVPressure() throws IOException {

		CSVPressureController csvPressureController = new CSVPressureController(pressureResponder);
		csvPressureController.pressure(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for temperature data.
	 */
	@Test
	public void testCSVTemperature() throws IOException {

		CSVTemperatureController csvTemperatureController = new CSVTemperatureController(temperatureResponder);
		csvTemperatureController.temperature(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	/**
	 * Tests the number of CSV columns for wind direction data.
	 */
	@Test
	public void testCSVWindDirection() throws IOException {

		CSVWindDirectionController csvWindDirectionController = new CSVWindDirectionController(windDirectionResponder);
		csvWindDirectionController.windDirection(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 3);
	}

	/**
	 * Tests the number of CSV columns for wind speed data.
	 */
	@Test
	public void testCSVWindSpeed() throws IOException {

		CSVWindSpeedController csvWindSpeedController = new CSVWindSpeedController(windSpeedResponder);
		csvWindSpeedController.windSpeed(TestConstant.LOCATION, fromDate, toDate, response);
		assertLength(sw, 4);

	}

	private void assertType(Iterable<HarmonieItem> list, Class<? extends HarmonieItem> cls) {

		Iterator<HarmonieItem> itr = list.iterator();
		HarmonieItem item = itr.next();
		Assert.assertTrue(cls.isInstance(item));

	}

}
