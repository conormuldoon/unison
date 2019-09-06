package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

public class HumidityResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	double humidity;

	public HumidityResult(Date date, double humidity) {
		this.date = date;
		this.humidity = humidity;
	}

	public HumidityResult() {

	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.println("date,humidity,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + humidity + ',');

	}
}
