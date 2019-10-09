package eu.acclimatize.unison;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.vividsolutions.jts.io.ParseException;

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
import eu.acclimatize.unison.location.LocationDetails;
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

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = { UnisonServerApplication.class, ResultQueryConfig.class, FinderConfig.class,
		CSVResponderConfig.class })
public class ControllerTests {

	HttpServletResponse response;

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	ItemListFinder cloudinessFinder, cloudLevelFinder, dewPointFinder, fogFinder, humidityFinder, precipitationFinder,
			pressureFinder, temperatureFinder, windDirectionFinder, windSpeedFinder;

	@Autowired
	CSVResponder cloudinessResponder, cloudLevelResponder, dewPointResponder, fogResponder, humidityResponder,
			precipitationResponder, pressureResponder, temperatureResponder, windDirectionResponder, windSpeedResponder;

	private Date fromDate, toDate;

	private final static String LOCATION = "UCD";

	// final private static String P_COORD = "POINT (-6.224133 53.308398)";

	private StringWriter sw;
	private LocationDetails location;
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

	@Before
	public void addWeatherData() throws ParseException {
		EntityManager em = testEntityManager.getEntityManager();

		UserInformation ui = new UserInformation("conor", "abc");
		em.persist(ui);

		location = new LocationDetails(LOCATION, "", ui);
		ik = new ItemKey(fromDate, location);

		WindDirection wd = new WindDirection(0, "Lett bris");
		WindSpeed ws = new WindSpeed(0, 0, "W");
		Cloud cloud = new Cloud(0d, 0d, 0d);
		WeatherValue weatherValue = new WeatherValue(0d, wd, ws, 0d, 0d, 0d, cloud, 0d, 0d);
		em.persist(location);
		HourlyWeather weatherData = new HourlyWeather(ik, weatherValue);
		em.persist(weatherData);

	}

	@Test
	public void testCloudiness() {

		CloudinessController cloudinessController = new CloudinessController(cloudinessFinder);

		assertType(cloudinessController.cloudiness(LOCATION, fromDate, toDate), CloudinessResult.class);

	}

	@Test
	public void testCloudLevel() {
		CloudLevelController cloudLevelController = new CloudLevelController(cloudLevelFinder);
		assertType(cloudLevelController.cloudLevel(LOCATION, fromDate, toDate), CloudLevelResult.class);

	}

	@Test
	public void testDewPoint() {
		DewPointController dewPointController = new DewPointController(dewPointFinder);
		assertType(dewPointController.dewPoint(LOCATION, fromDate, toDate), DewPointResult.class);

	}

	@Test
	public void testFog() {

		FogController fogController = new FogController(fogFinder);
		assertType(fogController.fog(LOCATION, fromDate, toDate), FogResult.class);

	}

	@Test
	public void testHumidity() {
		HumidityController humidityController = new HumidityController(humidityFinder);
		assertType(humidityController.humidity(LOCATION, fromDate, toDate), HumidityResult.class);

	}

	@Test
	public void testPreciptiation() throws ParseException {
		PrecipitationValue pv = new PrecipitationValue(0d, 0d, 0d);
		HourlyPrecipitation precip = new HourlyPrecipitation(ik, pv);
		testEntityManager.getEntityManager().persist(precip);

		PrecipitationController precipitationController = new PrecipitationController(precipitationFinder);
		assertType(precipitationController.precipitation(LOCATION, fromDate, toDate), PrecipitationResult.class);

	}

	@Test
	public void testPressure() {

		PressureController pressureController = new PressureController(pressureFinder);
		assertType(pressureController.pressure(LOCATION, fromDate, toDate), PressureResult.class);

	}

	@Test
	public void testTemperature() {

		TemperatureController temperatureController = new TemperatureController(temperatureFinder);
		assertType(temperatureController.temperature(LOCATION, fromDate, toDate), TemperatureResult.class);

	}

	@Test
	public void testWindDirection() {

		WindDirectionController windDirectionController = new WindDirectionController(windDirectionFinder);
		assertType(windDirectionController.windDirection(LOCATION, fromDate, toDate), WindDirectionResult.class);

	}

	@Test
	public void testWindSpeed() {

		WindSpeedController windSpeedController = new WindSpeedController(windSpeedFinder);
		assertType(windSpeedController.windSpeed(LOCATION, fromDate, toDate), WindSpeedResult.class);
	}

	void assertLength(StringWriter sw, int len) {
		String[] str = sw.toString().split("\n");
		String[] st0 = str[0].split(",");
		String[] st1 = str[1].split(",");
		Assert.assertEquals(len, st1.length);
		Assert.assertEquals(len, st0.length);
	}

	@Test
	public void testCSVCloudiness() throws IOException {

		CSVCloudinessController csvCloudinessController = new CSVCloudinessController(cloudinessResponder);
		csvCloudinessController.cloudiness(LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	@Test
	public void testCSVCloudLevel() throws IOException {

		CSVCloudLevelController csvCloudLevelController = new CSVCloudLevelController(cloudLevelResponder);
		csvCloudLevelController.cloudLevel(LOCATION, fromDate, toDate, response);
		assertLength(sw, 4);
	}

	@Test
	public void testCSVDewPoint() throws IOException {

		CSVDewPointController csvDewPointController = new CSVDewPointController(dewPointResponder);
		csvDewPointController.dewPoint(LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	@Test
	public void testCSVFog() throws IOException {

		CSVFogController csvFogController = new CSVFogController(fogResponder);
		csvFogController.fog(LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	@Test
	public void testCSVHumidity() throws IOException {

		CSVHumidityController csvHumidityController = new CSVHumidityController(humidityResponder);
		csvHumidityController.humidity(LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	@Test
	public void testCSVPrecipMultiple() throws IOException, ParseException {
		PrecipitationValue pv = new PrecipitationValue(0d, 0d, 0d);
		HourlyPrecipitation precip = new HourlyPrecipitation(ik, pv);
		testEntityManager.getEntityManager().persist(precip);
		CSVPrecipitationController csvPrecipitationController = new CSVPrecipitationController(precipitationResponder);
		csvPrecipitationController.precipitation(LOCATION, fromDate, toDate, response);
		assertLength(sw, 4);

	}

	@Test
	public void testCSVPrecipSingle() throws IOException, ParseException {
		PrecipitationValue pv = new PrecipitationValue(0d, null, null);
		HourlyPrecipitation precip = new HourlyPrecipitation(ik, pv);
		testEntityManager.getEntityManager().persist(precip);
		CSVPrecipitationController csvPrecipitationController = new CSVPrecipitationController(precipitationResponder);
		csvPrecipitationController.precipitation(LOCATION, fromDate, toDate, response);
		int len = 4;

		String[] str = sw.toString().split("\n");
		String[] st0 = str[0].split(",");
		String[] st1 = str[1].split(",");
		Assert.assertEquals(len, st1.length);
		Assert.assertEquals(len, st0.length);
		Assert.assertEquals("-1.0", st1[2]);
		Assert.assertEquals("-1.0", st1[3]);
	}

	@Test
	public void testCSVPressure() throws IOException {

		CSVPressureController csvPressureController = new CSVPressureController(pressureResponder);
		csvPressureController.pressure(LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	@Test
	public void testCSVTemperature() throws IOException {

		CSVTemperatureController csvTemperatureController = new CSVTemperatureController(temperatureResponder);
		csvTemperatureController.temperature(LOCATION, fromDate, toDate, response);
		assertLength(sw, 2);
	}

	@Test
	public void testCSVWindDirection() throws IOException {

		CSVWindDirectionController csvWindDirectionController = new CSVWindDirectionController(windDirectionResponder);
		csvWindDirectionController.windDirection(LOCATION, fromDate, toDate, response);
		assertLength(sw, 3);
	}

	@Test
	public void testCSVWindSpeed() throws IOException {

		CSVWindSpeedController csvWindSpeedController = new CSVWindSpeedController(windSpeedResponder);
		csvWindSpeedController.windSpeed(LOCATION, fromDate, toDate, response);
		assertLength(sw, 4);

	}

	void assertType(Iterable<HarmonieItem> list, Class<? extends HarmonieItem> cls) {

		Iterator<HarmonieItem> itr = list.iterator();
		HarmonieItem item = itr.next();
		Assert.assertTrue(cls.isInstance(item));

	}

}
