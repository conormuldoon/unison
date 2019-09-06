package eu.acclimatize.unison.csvcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.ItemListFinder;

public class CSVResponder {

	private final static String CSV_CONTENT = "text/csv";
	private ItemListFinder itemListFinder;

	public CSVResponder(ItemListFinder itemListFinder) {
		this.itemListFinder = itemListFinder;
	}

	public void handleResponse(HttpServletResponse response, String location, Date fromDate, Date toDate) throws IOException {
		response.setContentType(CSV_CONTENT);
		PrintWriter pw = response.getWriter();

		List<HarmonieItem> list = itemListFinder.find(location, fromDate, toDate);
		if (list.size() > 0) {
			list.get(0).printTitle(pw);
			for (HarmonieItem item : list) {
				item.printItem(pw);
			}
		}
		pw.close();
	}
}
