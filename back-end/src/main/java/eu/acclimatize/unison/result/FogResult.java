package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.csvcontroller.CSVHeaderItem;

/**
 * A Jackson and CSV annotated class that is used to store a result row from a
 * fog data query.
 *
 */
public class FogResult implements HarmonieItem {

	@CSVHeaderItem(Constant.FROM_HOUR)
	@JsonProperty
	private Date date;
	

	@CSVHeaderItem("Fog " + Constant.PERCENTAGE_SYMBOL)
	@JsonProperty
	private Double fog;

	/**
	 * Creates and instance of FogResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param fog  The amount of fog.
	 */
	public FogResult(Date date, Double fog) {
		this.date = date;
		this.fog = fog;
	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + fog + ',');

	}
}
