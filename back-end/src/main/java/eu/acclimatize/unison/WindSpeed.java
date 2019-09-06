package eu.acclimatize.unison;

import java.io.PrintWriter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WindSpeed implements HarmonieItem {

	@JsonProperty("mps")
	private double windSpeed_mps;
	@JsonProperty("beaufort")
	private int windSpeed_beaufort;
	@JsonProperty("name")
	private String windSpeed_name;

	public WindSpeed() {

	}

	public WindSpeed(double mps, int b, String n) {
		windSpeed_mps = mps;
		windSpeed_beaufort = b;
		windSpeed_name = n;
	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.println("mps,beaufort,name,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(windSpeed_mps + "," + windSpeed_beaufort + "," + windSpeed_name + ",");

	}

}
