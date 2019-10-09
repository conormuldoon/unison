package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

/**
 * A Jackson annotated class that is used to store a result row from a temperature data query.
 *
 */
public class TemperatureResult implements HarmonieItem {

	@JsonProperty
	private Date date;

	@JsonProperty
	private double temperature;

	/**
	 * Creates and instance of TemperatureResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param temperature The temperature level.
	 */
	public TemperatureResult(Date date, double temperature) {
		this.date = date;
		this.temperature = temperature;
	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + temperature + ',');

	}
}
