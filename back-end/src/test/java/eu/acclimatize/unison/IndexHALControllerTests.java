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

class IndexHALControllerTests {

	private BaseURIBuilder builder;
	private HttpServletResponse response;
	private HttpServletRequest request;

	@BeforeEach
	void init() {
		builder = Mockito.mock(BaseURIBuilder.class);
		request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(builder.build(request.getScheme(), request.getServerName(), request.getServerPort(),
				request.getContextPath())).thenReturn("http://localhost:8080");
		
		response = Mockito.mock(HttpServletResponse.class);
	}

	@Test
	void hasSelfRel() {

		IndexHALController controller = new IndexHALController(builder);
		UnisonModel model = controller.createModel(response, request);
		List<Link> list = model.getLinks("self");

		Assertions.assertTrue(list.size() > 0);

	}

	@Test
	void varyAcceptAdded() {

		IndexHALController controller = new IndexHALController(builder);
		controller.createModel(response, request);
		Mockito.verify(response).setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
	}

	@Test
	void portAdded() {
		BaseURIBuilder builder = new BaseURIBuilder();
		int port = 5000;
		String uri = builder.build("http", "localhost", port, "");
		Assertions.assertTrue(uri.contains(":" + port));
		uri = builder.build("https", "localhost", port, "");
		Assertions.assertTrue(uri.contains(":" + port));
	}
}
