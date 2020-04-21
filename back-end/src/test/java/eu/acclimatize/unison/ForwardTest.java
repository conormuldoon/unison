package eu.acclimatize.unison;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.HandlerMapping;

/**
 * A class for testing that requests beginning with /apiacc/ are prepended for forwarding.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ForwardTest {

	@Autowired
	private MappingForwardController forwardController;
	
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
}
