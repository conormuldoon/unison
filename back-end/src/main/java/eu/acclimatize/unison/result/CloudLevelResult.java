package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.Cloud;
import eu.acclimatize.unison.HarmonieItem;

/**
 * A Jackson annotated class that is used to store a result row from a low, medium, and high cloud level data query.
 *
 */
public class CloudLevelResult implements HarmonieItem {

	@JsonProperty
	private Date date;

	@JsonProperty
	private Cloud cloud;
	/**
	 * Creates and instance of CloudLevelResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param cloud Contains the low, medium, and high cloud levels.
	 */
	public CloudLevelResult(Date date, Cloud cloud) {
		this.date = date;
		this.cloud = cloud;
	}


	/**
	 * Prints the title/header in CSV format.
	 * @param pw The writer the title is printed to.
	 */
	public static void printTitle(PrintWriter pw) {
		pw.print("date,");
		Cloud.printTitle(pw);

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');
		cloud.printItem(pw);

	}
}
