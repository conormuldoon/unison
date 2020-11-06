package eu.acclimatize.unison;

import java.io.PrintWriter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.acclimatize.unison.csvcontroller.CSVHeaderItem;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Stores the median, 20<sup>th</sup> percentile, and 80<sup>th</sup> percentile
 * precipitation values.
 *
 */
@JsonInclude(Include.NON_NULL)
public class PrecipitationValue implements HarmonieItem {

	@CSVHeaderItem("Median " + Constant.MILLIMETRES_SYMBOL)
	@JsonProperty
	private Double value;

	@CSVHeaderItem("20th percentile " + Constant.MILLIMETRES_SYMBOL)
	@JsonProperty
	private Double minvalue;

	@CSVHeaderItem("80th percentile " + Constant.MILLIMETRES_SYMBOL)
	@JsonProperty
	private Double maxvalue;

	/**
	 * Creates an instance of PrecipitationValue.
	 * 
	 * @param value    The median value.
	 * @param minvalue The 20<sup>th</sup> percentile value.
	 * @param maxvalue The 80<sup>th</sup> percentile value.
	 */
	public PrecipitationValue(Double value, Double minvalue, Double maxvalue) {
		this.value = value;
		this.minvalue = minvalue;
		this.maxvalue = maxvalue;
	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public PrecipitationValue() {

	}

	public boolean ternary() {
		return minvalue != null && maxvalue != null;
	}

	@Override
	public void printItem(PrintWriter pw) {
		if (ternary()) {
			pw.println(value + "," + minvalue + "," + maxvalue + ",");
		} else {
			pw.println(value + ",-1.0,-1.0,");
		}

	}

}
