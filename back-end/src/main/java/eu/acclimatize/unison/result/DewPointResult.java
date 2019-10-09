package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

/**
 * A Jackson annotated class that is used to store a result row from a dew point data query.
 *
 */
public class DewPointResult implements HarmonieItem {

	@JsonProperty
	private Date date;

	@JsonProperty
	private double dewPoint;

	/**
	 * Creates and instance of DewPointResulte.
	 * 
	 * @param date The hour from which the results relates.
	 * @param dewPoint The dew point value.
	 */
	public DewPointResult(Date date, double dewPoint) {
		this.date = date;
		this.dewPoint = dewPoint;
	}
	

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + dewPoint + ',');

	}
}
