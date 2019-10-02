package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.WindDirection;

/**
 * A Jackson annotated class that is used to store a result row from a wind direction data query.
 *
 */
public class WindDirectionResult implements HarmonieItem {

	@JsonProperty
	private Date date;

	@JsonProperty
	private WindDirection windDirection;

	/**
	 * Creates and instance of WindDirectionResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param windDirection Contains information related to the angle in degrees and wind name.
	 */
	public WindDirectionResult(Date date, WindDirection windDirection) {
		this.date = date;
		this.windDirection = windDirection;
	}

	/**
	 * Prints the title/header in CSV format.
	 * @param pw The writer the title is printed to.
	 */
	public static void printTitle(PrintWriter pw) {
		pw.print("date,");
		WindDirection.printTitle(pw);

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');

		windDirection.printItem(pw);
	}
}
