package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

public class PressureResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	double pressure;

	public PressureResult(Date date, double pressure) {
		this.date = date;
		this.pressure = pressure;
	}

	public PressureResult() {

	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.println("date,pressure,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + pressure + ',');

	}
}
