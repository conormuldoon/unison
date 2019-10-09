package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

/**
 * A Jackson annotated class that is used to store a result row from a fog data query.
 *
 */
public class FogResult implements HarmonieItem {

	@JsonProperty
	private Date date;

	@JsonProperty
	private double fog;

	/**
	 * Creates and instance of FogResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param fog The amount of fog.
	 */
	public FogResult(Date date, double fog) {
		this.date = date;
		this.fog = fog;
	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + fog + ',');

	}
}
