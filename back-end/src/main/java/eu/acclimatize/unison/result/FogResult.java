package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

public class FogResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	double fog;

	public FogResult(Date date, double fog) {
		this.date = date;
		this.fog = fog;
	}

	public FogResult() {

	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.println("date,fog,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + fog + ',');

	}
}
