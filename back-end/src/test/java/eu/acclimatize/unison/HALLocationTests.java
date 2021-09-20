package eu.acclimatize.unison;

import java.util.Optional;

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
public class HALLocationTests {
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

	/**
	 * Adds an initial user and location to the database.
	 */
	@BeforeEach
	public void addData() {
		RootURIBuilder builder = Mockito.mock(RootURIBuilder.class);
		Mockito.when(builder.build()).thenReturn("http://localhost:8080");
		controller = new HALLocationController(locationService, weatherLink, builder);
		response = Mockito.mock(HttpServletResponse.class);

		TestUtility.saveLocationData(userRepository, locationRepository);

	}

	/**
	 * Removes all data from the location and user repositories.
	 */
	@AfterEach
	public void clearData() {
		TestUtility.deleteLocationData(locationRepository, userRepository);
	}

	private void hasSelf(RepresentationModel<?> model) {
		Optional<Link> oLink = model.getLink("self");
		Assertions.assertTrue(oLink.isPresent());
	}

	@Test
	public void lcSelfRef() {

		CollectionModel<LocationModel> model = controller.createModel(response);
		hasSelf(model);

	}

	@Test
	void locaitonSelfRef() {

		LocationModel model = controller.location(response, TestConstant.LOCATION);
		hasSelf(model);
	}

	private void verifyVaryAccept() {
		Mockito.verify(response).setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
	}

	@Test
	public void lcVaryAccept() {
		controller.createModel(response);
		verifyVaryAccept();
	}

	@Test
	public void locationVaryAccept() {
		controller.location(response, TestConstant.LOCATION);
		verifyVaryAccept();
	}
}
