package eu.acclimatize.unison;

import java.io.PrintWriter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Stores the median, 20<sup>th</sup> percentile, and 80<sup>th</sup> percentile precipitation values.
 *
 */
@JsonInclude(Include.NON_NULL)
public class PrecipitationValue implements HarmonieItem {

	@JsonProperty
	private Double value, minvalue, maxvalue;

	/**
	 * Creates an instance of PrecipitationValue.
	 * 
	 * @param v The median value.
	 * @param mnv The 20<sup>th</sup> percentile value.
	 * @param mxv The 80<sup>th</sup> percentile value.
	 */
	public PrecipitationValue(Double v, Double mnv, Double mxv) {
		value = v;
		minvalue = mnv;
		maxvalue = mxv;
	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public PrecipitationValue() {

	}

	/**
	 * Prints the title/header in CSV format.
	 * @param pw The writer the title is printed to.
	 */
	public static void printTitle(PrintWriter pw) {
		
		pw.println("value,minValue,maxValue,");
	

	}
	
	@Override
	public void printItem(PrintWriter pw) {
		if (minvalue != null) {
			pw.println(value + "," + minvalue + "," + maxvalue + ",");
		} else {
			pw.println(value + ",-1.0,-1.0,");
		}

	}

}
