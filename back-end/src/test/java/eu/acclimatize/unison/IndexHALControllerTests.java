package eu.acclimatize.unison;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;

public class IndexHALControllerTests {

	private RootURIBuilder builder;
	private HttpServletResponse response;
	private HttpServletRequest request;

	@BeforeEach
	public void init() {
		builder = Mockito.mock(RootURIBuilder.class);
		request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(builder.build(request)).thenReturn("http://localhost:8080");
		response = Mockito.mock(HttpServletResponse.class);
	}

	@Test
	public void hasSelfRel() {

		IndexHALController controller = new IndexHALController(builder);
		UnisonModel model = controller.createModel(response, request);
		List<Link> list = model.getLinks("self");

		Assertions.assertTrue(list.size() > 0);

	}

	@Test
	public void varyAcceptAdded() {

		IndexHALController controller = new IndexHALController(builder);
		controller.createModel(response, request);
		Mockito.verify(response).setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
	}

	@Test
	public void portAdded() {
		RootURIBuilder builder = new RootURIBuilder();
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		int port = 5000;
		Mockito.when(request.getServerPort()).thenReturn(port);
		Mockito.when(request.getScheme()).thenReturn("http");
		String uri = builder.build(request);
		Assertions.assertTrue(uri.contains(":" + port));
		Mockito.when(request.getScheme()).thenReturn("https");
		uri = builder.build(request);
		Assertions.assertTrue(uri.contains(":" + port));
	}
}
