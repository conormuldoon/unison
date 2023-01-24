package eu.acclimatize.unison.jsoncontroller;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eu.acclimatize.unison.HourlyPrecipitationRepository;
import eu.acclimatize.unison.TestConstant;
import eu.acclimatize.unison.TestUtility;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.result.PrecipitationResult;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * Tests the JSON controller for querying precipitation data.
 *
 */
@SpringBootTest
class JSONPrecipitationTest {

	@Autowired
	private JSONPrecipitationController precipitationController;

	@Autowired
	private HourlyPrecipitationRepository hpr;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Test that the results returned are of type PrecipitationResult.
	 */
	@Test
	void testPreciptiation() {

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		TestUtility.savePrecipitationData(userRepository, locationRepository, hpr);

		TestUtility.assertType(precipitationController.precipitation(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), PrecipitationResult.class);

		TestUtility.deletePrecipitationData(hpr, locationRepository, userRepository);
	}

}
