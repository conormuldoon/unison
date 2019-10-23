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
	private Double value;
	
	@JsonProperty
	private Double minvalue;
	
	@JsonProperty
	private Double maxvalue;

	/**
	 * Creates an instance of PrecipitationValue.
	 * 
	 * @param value The median value.
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
	
	@Override
	public void printItem(PrintWriter pw) {
		if (minvalue != null) {
			pw.println(value + "," + minvalue + "," + maxvalue + ",");
		} else {
			pw.println(value + ",-1.0,-1.0,");
		}

	}

}
