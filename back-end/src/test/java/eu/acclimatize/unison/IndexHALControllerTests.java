package eu.acclimatize.unison;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;

public class IndexHALControllerTests {

	@Test
	public void hasSelfRel() {
		RootURIBuilder builder = Mockito.mock(RootURIBuilder.class);
		Mockito.when(builder.build()).thenReturn("http://localhost:8080");
		IndexHALController controller = new IndexHALController(builder);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		UnisonModel model = controller.createModel(response);
		List<Link> list = model.getLinks("self");

		Assertions.assertTrue(list.size() > 0);

	}

	@Test
	public void varyAcceptAdded() {
		RootURIBuilder builder = Mockito.mock(RootURIBuilder.class);
		Mockito.when(builder.build()).thenReturn("http://localhost:8080");
		IndexHALController controller = new IndexHALController(builder);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		controller.createModel(response);
		Mockito.verify(response).setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);
	}
}
