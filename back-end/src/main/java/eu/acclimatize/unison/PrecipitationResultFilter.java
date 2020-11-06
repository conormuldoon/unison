package eu.acclimatize.unison;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import eu.acclimatize.unison.result.PrecipitationResult;

/**
 * A class that filters precipitation results, such that only ternary
 * precipitation results or single value results are returned, not a
 * combination.
 *
 */
public class PrecipitationResultFilter {

	private ItemListFinder<PrecipitationResult> precipitationFinder;

	/**
	 * Creates an instance of PrecipitationResultFilter.
	 * 
	 * @param precipitationFinder The finder used to obtain precipitation results
	 *                            for queries.
	 */
	public PrecipitationResultFilter(ItemListFinder<PrecipitationResult> precipitationFinder) {
		this.precipitationFinder = precipitationFinder;
	}

	/**
	 * Filters results such that only ternary or single values are contained in the
	 * returned list, not a combination of both.
	 * 
	 * @param response The response object used to alter the default headers.
	 * @param location The query location.
	 * @param fromDate The earliest date in the query range.
	 * @param toDate The latest date in the query range.
	 * @return The filtered list.
	 */
	public List<HarmonieItem> filterResults(HttpServletResponse response, String location, Date fromDate, Date toDate) {

		List<PrecipitationResult> list = precipitationFinder.find(response, location, fromDate, toDate);

		ArrayList<HarmonieItem> ret = new ArrayList<>();
		int n = list.size();

		if (n > 0) {
			PrecipitationResult first = list.get(0);
			boolean firstT = first.ternary();
			ret.add(first);
			for (int i = 1; i < n; i++) {
				PrecipitationResult pr = list.get(i);
				if (pr.ternary() == firstT) {
					ret.add(pr);
				}
			}

		}
		return ret;
	}

}
