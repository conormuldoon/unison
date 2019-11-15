package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.CSVHeaderItem;
import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.WindSpeed;

/**
 * A Jackson and CSV annotated class that is used to store a result row from a wind speed data query.
 *
 */
public class WindSpeedResult implements HarmonieItem {

	@CSVHeaderItem(Constant.FROM_HOUR)
	@JsonProperty
	private Date date;

	@CSVHeaderItem
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

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');
		windSpeed.printItem(pw);

	}
}
