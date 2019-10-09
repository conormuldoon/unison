package eu.acclimatize.unison;

import java.io.PrintWriter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class for the wind direction angle and name.
 *
 */
public class WindDirection implements HarmonieItem {

	@JsonProperty("degress")
	private double windDirection_deg;
	@JsonProperty("name")
	private String windDirection_name;

	/**
	 * A zero argument constructor for JPA.
	 */
	public WindDirection() {

	}

	/**
	 * Creates an instance of WindDirection.
	 * 
	 * @param d The wind direction in degrees.
	 * @param n The wind direction name.
	 */
	public WindDirection(double d, String n) {
		windDirection_deg = d;
		windDirection_name = n;
	}

	/**
	 * Converts the wind direction angle to the sin of the angle.
	 * 
	 * @return The sin of the angle.
	 */
	@JsonProperty
	public double sinDeg() {
		return Math.sin(Math.toRadians(windDirection_deg));
	}



	@Override
	public void printItem(PrintWriter pw) {
		pw.println(windDirection_deg + "," + windDirection_name + ',');

	}

}
