package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.csvcontroller.CSVHeaderItem;

/**
 * A Jackson and CSV annotated class that is used to store a result row from a
 * global radiation data query.
 *
 */
public class GlobalRadiationResult implements HarmonieItem {

	@CSVHeaderItem(Constant.FROM_HOUR)
	@JsonProperty
	private Date date;

	@CSVHeaderItem("Global Radiation (W/m^2)")
	@JsonProperty
	private Double globalRadiation;

	/**
	 * Creates and instance of GlobalRadiationResult.
	 * 
	 * @param date            The hour from which the results relates.
	 * @param globalRadiation The level of global radiation.
	 */
	public GlobalRadiationResult(Date date, Double globalRadiation) {
		this.date = date;
		this.globalRadiation = globalRadiation;
	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + globalRadiation + ',');

	}
}
