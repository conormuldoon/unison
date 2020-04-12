package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.WindDirection;
import eu.acclimatize.unison.csvcontroller.CSVHeaderItem;

/**
 * A Jackson and CSV annotated class that is used to store a result row from a wind direction data query.
 *
 */
public class WindDirectionResult implements HarmonieItem {

	@CSVHeaderItem(Constant.FROM_HOUR)
	@JsonProperty
	private Date date;

	@CSVHeaderItem
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

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');

		windDirection.printItem(pw);
	}
}
