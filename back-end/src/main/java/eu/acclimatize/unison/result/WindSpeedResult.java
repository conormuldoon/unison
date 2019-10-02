package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.WindSpeed;

/**
 * A Jackson annotated class that is used to store a result row from a wind speed data query.
 *
 */
public class WindSpeedResult implements HarmonieItem {

	@JsonProperty
	private Date date;

	@JsonProperty
	private WindSpeed windSpeed;

	/**
	 * Creates and instance of WindSpeedResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param windSpeed Contains information about the wind speed in
	 *  metres per second, the wind name, and the Beaufort scale value.
	 */
	public WindSpeedResult(Date date, WindSpeed windSpeed) {
		this.date = date;
		this.windSpeed = windSpeed;
	}


	/**
	 * Prints the title/header in CSV format.
	 * @param pw The writer the title is printed to.
	 */
	public static void printTitle(PrintWriter pw) {
		pw.print("date,");
		WindSpeed.printTitle(pw);

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');
		windSpeed.printItem(pw);

	}
}
