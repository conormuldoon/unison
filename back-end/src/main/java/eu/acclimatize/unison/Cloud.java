package eu.acclimatize.unison;

import java.io.PrintWriter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cloud implements HarmonieItem {

	@JsonProperty("low")
	private Double lowClouds;

	@JsonProperty("medium")
	private Double mediumClouds;

	@JsonProperty("high")
	private Double highClouds;

	public Cloud(Double lowClouds, Double mediumClouds, Double highClouds) {
		this.lowClouds = lowClouds;
		this.mediumClouds = mediumClouds;
		this.highClouds = highClouds;
	}
	
	public Cloud() {
		
	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.println("low,medium,high,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(lowClouds + "," + mediumClouds + "," + highClouds + ",");

	}
}
