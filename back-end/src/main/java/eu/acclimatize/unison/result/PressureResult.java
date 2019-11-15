package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.CSVHeaderItem;
import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;

/**
 * A Jackson and CSV annotated class that is used to store a result row from a pressure data query.
 *
 */
public class PressureResult implements HarmonieItem {

	@CSVHeaderItem(Constant.FROM_HOUR)
	@JsonProperty
	private Date date;

	@CSVHeaderItem("Pressure (hPa)")
	@JsonProperty
	private double pressure;

	/**
	 * Creates and instance of PressureResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param pressure The level of pressure.
	 */
	public PressureResult(Date date, double pressure) {
		this.date = date;
		this.pressure = pressure;
	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + pressure + ',');

	}
}
