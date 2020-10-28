package eu.acclimatize.unison;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;

public class CacheSupport {

	public void addHeader(Date toDate, HttpServletResponse response) {

		response.setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);

		Date today = Calendar.getInstance().getTime();

		if (toDate.before(today)) {
			// 2628000 seconds is 1 month
			response.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=2628000, immutable");
		}

	}

}
