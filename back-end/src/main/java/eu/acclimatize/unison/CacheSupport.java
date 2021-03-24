package eu.acclimatize.unison;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;

/**
 * Used for adding conditional the Vary header and a conditional cache control header.
 *
 */
public class CacheSupport {

	private Date today() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * Adds Vary and conditional cache control headers.
	 * 
	 * @param toDate The end date for the request.
	 * @param response The response to add headers to.
	 */
	public void addHeader(Date toDate, HttpServletResponse response) {

		response.setHeader(HttpHeaders.VARY, HttpHeaders.ACCEPT);

		Date today = today();

		if (toDate.before(today)) {
			// 2628000 seconds is 1 month
			response.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=2628000, immutable");
		}

	}

}
