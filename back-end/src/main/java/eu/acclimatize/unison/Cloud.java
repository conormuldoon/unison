package eu.acclimatize.unison;

import java.io.PrintWriter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * A class for low, medium, and high cloud levels.
 *
 */
public class Cloud implements HarmonieItem {

	@JsonProperty("low")
	private Double lowClouds;

	@JsonProperty("medium")
	private Double mediumClouds;

	@JsonProperty("high")
	private Double highClouds;

	/**
	 * Creates an instance of Cloud.
	 * 
	 * @param lowClouds The low clouds percentage.
	 * @param mediumClouds The medium clouds percentage.
	 * @param highClouds The high clouds percentage.
	 */
	public Cloud(Double lowClouds, Double mediumClouds, Double highClouds) {
		this.lowClouds = lowClouds;
		this.mediumClouds = mediumClouds;
		this.highClouds = highClouds;
	}
	
	/**
	 * A zero argument constructor for JPA.
	 */
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
