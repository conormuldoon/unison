package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

public class TemperatureResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	double temperature;

	public TemperatureResult(Date date, double temperature) {
		this.date = date;
		this.temperature = temperature;
	}

	public TemperatureResult() {

	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.println("date,temperature,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + temperature + ',');

	}
}
