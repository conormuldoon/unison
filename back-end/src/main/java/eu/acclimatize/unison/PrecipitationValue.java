package eu.acclimatize.unison;

import java.io.PrintWriter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PrecipitationValue implements HarmonieItem {

	@JsonProperty
	private Double value, minvalue, maxvalue;

	public PrecipitationValue(Double v, Double mnv, Double mxv) {
		value = v;
		minvalue = mnv;
		maxvalue = mxv;
	}

	public PrecipitationValue() {

	}

	@Override
	public void printTitle(PrintWriter pw) {
		if (minvalue != null) {
			pw.println("value,minValue,maxValue,");
		} else {
			pw.println("value,");
		}

	}
	
	@Override
	public void printItem(PrintWriter pw) {
		if (minvalue != null) {
			pw.println(value + "," + minvalue + "," + maxvalue + ",");
		} else {
			pw.println(value + ",");
		}

	}

}
