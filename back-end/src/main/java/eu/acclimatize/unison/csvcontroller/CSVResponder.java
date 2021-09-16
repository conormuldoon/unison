package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;

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


	private String uVariableName;
	private DateFormat dateFormat;

	/**
	 * Creates an instance of CSVResponder.
	 * 
	 * @param itemListFinder Used to obtain a list of data based on query
	 *                       parameters.
	 * @param header         The CSV header printed by the responder.
	 * @param cacheSupport   Used for adding conditional the Vary header and a
	 *                       conditional cache control header.
	 * @param variableName   The name of the weather variable with spaces
	 *                       underscored.
	 * @param dateFormat     the date format used when specifying the file name in
	 *                       the content disposition header.
	 */

	public CSVResponder(ItemListFinder<? extends HarmonieItem> itemListFinder, String header, CacheSupport cacheSupport,
			String uVariableName, DateFormat dateFormat) {
		this.itemListFinder = itemListFinder;
		this.header = header;
		this.uVariableName = uVariableName;
		this.dateFormat = dateFormat;
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

		List<? extends HarmonieItem> list = itemListFinder.find(response, location, fromDate, toDate);

		handleResponse(response, location, fromDate, toDate, list);
	}

	/**
	 * Writes the list to the HTTP servlet response object in a CSV format and
	 * alters caching headers subject to the last date for the query range.
	 * 
	 * @param fromDate    The start date for the data (inclusive).
	 * @param toDate      The end date for the data (inclusive).
	 * @param locatinName The name of the location for the data.
	 * @param response    Data is written to the writer of the response object and
	 *                    the content type is set to text/csv.
	 * @throws IOException Thrown if there is a problem obtaining the writer of the
	 *                     response object.
	 */
	public void handleResponse(HttpServletResponse response, String locationName, Date fromDate, Date toDate,
			List<? extends HarmonieItem> list) throws IOException {
		response.setContentType(CSV_CONTENT);

		String uLoc = locationName.replaceAll(" ", "_");

		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uLoc + "_" + uVariableName + "_"
				+ dateFormat.format(fromDate) + "_" + dateFormat.format(toDate) + ".csv\"");

		PrintWriter pw = response.getWriter();

		pw.println(header);

		for (HarmonieItem item : list) {
			item.printItem(pw);
		}

		pw.close();
	}
}
