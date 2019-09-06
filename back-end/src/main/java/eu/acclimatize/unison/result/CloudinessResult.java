package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;

public class CloudinessResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	double cloudiness;

	public CloudinessResult(Date date, double cloudiness) {
		this.date = date;
		this.cloudiness = cloudiness;
	}

	public CloudinessResult() {

	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.println("date,cloudiness,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(date.toString() + ',' + cloudiness + ',');

	}
}
