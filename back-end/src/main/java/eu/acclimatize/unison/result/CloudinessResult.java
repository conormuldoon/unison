package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

/**
 * A Jackson annotated class that is used to store a result row from a cloudiness data query.
 *
 */
public class CloudinessResult implements HarmonieItem {

	@JsonProperty
	private Date date;

	@JsonProperty
	private double cloudiness;

	/**
	 * Creates and instance of CloudinessResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param cloudiness The percentage of cloudiness.
	 */
	public CloudinessResult(Date date, double cloudiness) {
		this.date = date;
		this.cloudiness = cloudiness;
	}


	/**
	 * Prints the title/header in CSV format.
	 * @param pw The writer the title is printed to.
	 */
	public static void printTitle(PrintWriter pw) {
		pw.println("date,cloudiness,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + cloudiness + ',');

	}
}
