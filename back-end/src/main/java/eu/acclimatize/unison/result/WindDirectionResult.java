package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.WindDirection;

public class WindDirectionResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	WindDirection windDirection;

	public WindDirectionResult(Date date, WindDirection windDirection) {
		this.date = date;
		this.windDirection = windDirection;
	}

	public WindDirectionResult() {
		windDirection=new WindDirection();
	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.print("date,");
		windDirection.printTitle(pw);

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');

		windDirection.printItem(pw);
	}
}
