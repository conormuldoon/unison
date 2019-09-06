package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.WindSpeed;

public class WindSpeedResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	WindSpeed windSpeed;

	public WindSpeedResult(Date date, WindSpeed windSpeed) {
		this.date = date;
		this.windSpeed = windSpeed;
	}

	public WindSpeedResult() {
		windSpeed = new WindSpeed();
	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.print("date,");
		windSpeed.printTitle(pw);

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');
		windSpeed.printItem(pw);

	}
}
