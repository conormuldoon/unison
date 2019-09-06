package eu.acclimatize.unison;

import java.io.PrintWriter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WindDirection implements HarmonieItem {

	@JsonProperty("degress")
	private double windDirection_deg;
	@JsonProperty("name")
	private String windDirection_name;

	public WindDirection() {

	}

	public WindDirection(double d, String n) {
		windDirection_deg = d;
		windDirection_name = n;
	}

	@JsonProperty
	public double sinDeg() {
		return Math.sin(Math.toRadians(windDirection_deg));
	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.println("degrees,name,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(windDirection_deg + "," + windDirection_name + ',');

	}

}
