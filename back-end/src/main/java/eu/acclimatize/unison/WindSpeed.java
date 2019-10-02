package eu.acclimatize.unison;

import java.io.PrintWriter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class for the wind speed in metres per second, Beaufort value, and wind name.
 *
 */
public class WindSpeed implements HarmonieItem {

	@JsonProperty("mps")
	private double windSpeed_mps;
	@JsonProperty("beaufort")
	private int windSpeed_beaufort;
	@JsonProperty("name")
	private String windSpeed_name;

	/**
	 * A zero argument constructor for JPA.
	 */
	public WindSpeed() {

	}

	/**
	 * Creates an instance of WindSpeed
	 * 
	 * @param mps The wind speed in metres per second.
	 * @param b The Beaufort scale value.
	 * @param n The wind speed name.
	 */
	public WindSpeed(double mps, int b, String n) {
		windSpeed_mps = mps;
		windSpeed_beaufort = b;
		windSpeed_name = n;
	}

	/**
	 * Prints the title/header in CSV format.
	 * @param pw The writer the title is printed to.
	 */
	public static void printTitle(PrintWriter pw) {
		pw.println("mps,beaufort,name,");

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(windSpeed_mps + "," + windSpeed_beaufort + "," + windSpeed_name + ",");

	}

}
