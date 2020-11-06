package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import eu.acclimatize.unison.CacheSupport;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.ItemListFinder;

/**
 * Prints weather and precipitation data in a CSV format.
 *
 */
public class CSVResponder {

	private static final String CSV_CONTENT = "text/csv";
	private ItemListFinder<? extends HarmonieItem> itemListFinder;
	private String header;

	private CacheSupport cacheSupport;
	/**
	 * Creates an instance of CSVResponder.
	 * 
	 * @param itemListFinder Used to obtain a list of data based on query
	 *                       parameters.
	 * @param header         The CSV header printed by the responder.
	 */

	public CSVResponder(ItemListFinder<? extends HarmonieItem> itemListFinder, String header, CacheSupport cacheSupport) {
		this.itemListFinder = itemListFinder;
		this.header = header;
		this.cacheSupport=cacheSupport;
	}

	/**
	 * Uses an {@link ItemListFinder} to obtain a list of items based on query
	 * parameters and writes the list to the HTTP servlet response object in a CSV
	 * format.
	 * 
	 * @param location The location of interest.
	 * @param fromDate The start date for the data (inclusive).
	 * @param toDate   The end date for the data (inclusive).
	 * @param response Data is written to the writer of the response object and the
	 *                 content type is set to text/csv.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the
	 *                     response object.
	 */
	public void handleResponse(HttpServletResponse response, String location, Date fromDate, Date toDate)
			throws IOException {
		response.setContentType(CSV_CONTENT);
		cacheSupport.addHeader(toDate, response);
		
		PrintWriter pw = response.getWriter();

		pw.println(header);

		List<? extends HarmonieItem> list = itemListFinder.find(response, location, fromDate, toDate);

		for (HarmonieItem item : list) {
			item.printItem(pw);
		}

		pw.close();
	}
}
