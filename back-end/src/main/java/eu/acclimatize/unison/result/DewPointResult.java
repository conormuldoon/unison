package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

public class DewPointResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	double dewPoint;

	public DewPointResult(Date date, double dewPoint) {
		this.date = date;
		this.dewPoint = dewPoint;
	}

	public DewPointResult() {

	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.println("date,dewPoint,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + dewPoint + ',');

	}
}
