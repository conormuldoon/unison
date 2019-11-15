package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.CSVHeaderItem;
import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;

/**
 * A Jackson and CSV annotated class that is used to store a result row from a
 * cloudiness data query.
 *
 */
public class CloudinessResult implements HarmonieItem {

	@CSVHeaderItem(Constant.FROM_HOUR)
	@JsonProperty
	private Date date;

	@CSVHeaderItem("Cloudiness " + Constant.PERCENTAGE_SYMBOL)
	@JsonProperty
	private double cloudiness;

	/**
	 * Creates and instance of CloudinessResult.
	 * 
	 * @param date       The hour from which the results relates.
	 * @param cloudiness The percentage of cloudiness.
	 */
	public CloudinessResult(Date date, double cloudiness) {
		this.date = date;
		this.cloudiness = cloudiness;
	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + cloudiness + ',');

	}
}
