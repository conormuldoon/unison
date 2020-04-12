package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.csvcontroller.CSVHeaderItem;

/**
 * A Jackson and CSV annotated class that is used to store a result row from a
 * humidity data query.
 *
 */
public class HumidityResult implements HarmonieItem {

	@CSVHeaderItem(Constant.FROM_HOUR)
	@JsonProperty
	private Date date;

	@CSVHeaderItem("Humidity " + Constant.PERCENTAGE_SYMBOL)
	@JsonProperty
	private double humidity;

	/**
	 * Creates and instance of HumidityResult.
	 * 
	 * @param date     The hour from which the results relates.
	 * @param humidity The level of humidity.
	 */
	public HumidityResult(Date date, double humidity) {
		this.date = date;
		this.humidity = humidity;
	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + humidity + ',');

	}
}
