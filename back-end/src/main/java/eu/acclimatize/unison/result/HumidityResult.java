package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

/**
 * A Jackson annotated class that is used to store a result row from a humidity data query.
 *
 */
public class HumidityResult implements HarmonieItem {

	@JsonProperty
	private Date date;

	@JsonProperty
	private double humidity;

	/**
	 * Creates and instance of HumidityResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param humidity The level of humidity.
	 */
	public HumidityResult(Date date, double humidity) {
		this.date = date;
		this.humidity = humidity;
	}

	/**
	 * Prints the title/header in CSV format.
	 * @param pw The writer the title is printed to.
	 */
	public static void printTitle(PrintWriter pw) {
		pw.println("date,humidity,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + humidity + ',');

	}
}
