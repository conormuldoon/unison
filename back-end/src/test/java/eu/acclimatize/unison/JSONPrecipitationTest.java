package eu.acclimatize.unison;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import eu.acclimatize.unison.jsoncontroller.JSONPrecipitationController;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.result.PrecipitationResult;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * Tests the JSON controller for querying precipitation data.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class JSONPrecipitationTest {

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
	public void testPreciptiation() {

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		TestUtility.savePrecipitationData(userRepository, locationRepository, hpr);

		TestUtility.assertType(precipitationController.precipitation(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE, response), PrecipitationResult.class);

		TestUtility.deletePrecipitationData(hpr, locationRepository, userRepository);
	}

}
