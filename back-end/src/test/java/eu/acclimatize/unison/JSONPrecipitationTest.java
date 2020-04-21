package eu.acclimatize.unison;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import eu.acclimatize.unison.jsoncontroller.PrecipitationController;
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
	private PrecipitationController precipitationController;

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

		TestUtility.savePrecipitationData(userRepository, locationRepository,hpr);

		TestUtility.assertType(precipitationController.precipitation(TestConstant.LOCATION, TestConstant.FROM_DATE,
				TestConstant.TO_DATE), PrecipitationResult.class);

		TestUtility.deletePrecipitationData(hpr, locationRepository, userRepository);
	}

}
