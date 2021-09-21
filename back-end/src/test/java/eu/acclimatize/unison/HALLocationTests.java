package eu.acclimatize.unison;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import eu.acclimatize.unison.location.HALLocationController;
import eu.acclimatize.unison.location.LocationConfig;
import eu.acclimatize.unison.location.LocationModel;
import eu.acclimatize.unison.location.LocationRepository;
import eu.acclimatize.unison.location.LocationService;
import eu.acclimatize.unison.location.WeatherLink;
import eu.acclimatize.unison.user.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UnisonServerApplication.class, LocationConfig.class })
class HALLocationTests {
	@Autowired
	private LocationService locationService;

	@Autowired
	private WeatherLink[] weatherLink;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	private HALLocationController controller;

	private HttpServletResponse response;

	private HttpServletRequest request;

	/**
	 * .
	 */
	@BeforeEach
	void init() {
		request = Mockito.mock(HttpServletRequest.class);
		BaseURIBuilder builder = Mockito.mock(BaseURIBuilder.class);
		Mockito.when(builder.build(request.getScheme(), request.getServerName(), request.getServerPort(),
				request.getContextPath())).thenReturn("http://localhost:8080");
		controller = new HALLocationController(locationService, weatherLink, builder);
		response = Mockito.mock(HttpServletResponse.class);

		TestUtility.saveLocationData(userRepository, locationRepository);

	}

	/**
	 * Removes all data from the location and user repositories.
	 */
	@AfterEach
	void clearData() {
		TestUtility.deleteLocationData(locationRepository, userRepository);
	}

	private void hasSelf(RepresentationModel<?> model) {
		Optional<Link> oLink = model.getLink("self");
		Assertions.assertTrue(oLink.isPresent());
	}

	@Test
	void lcSelfRef() {

		CollectionModel<LocationModel> model = controller.createModel(response, request);
		hasSelf(model);

	}

	@Test
	void locaitonSelfRef() {

		LocationModel model = controller.location(response, request, TestConstant.LOCATION);
		hasSelf(model);
	}

	private void verifyVaryAccept() {
		Mockito.verify(response).setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
	}

	@Test
	void lcVaryAccept() {
		controller.createModel(response, request);
		verifyVaryAccept();
	}

	@Test
	void locationVaryAccept() {
		controller.location(response, request, TestConstant.LOCATION);
		verifyVaryAccept();
	}
}
