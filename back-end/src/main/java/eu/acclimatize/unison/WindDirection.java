package eu.acclimatize.unison;

import java.io.PrintWriter;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.csvcontroller.CSVHeaderItem;

/**
 * A class for the wind direction angle and name.
 *
 */
public class WindDirection implements HarmonieItem {

	@CSVHeaderItem("Degrees")
	@JsonProperty
	@Column(name="windDirection_deg")
	private double degrees;
	
	@CSVHeaderItem("Name")
	@JsonProperty
	@Column(name="windDirection_name")
	private String name;

	/**
	 * A zero argument constructor for JPA.
	 */
	public WindDirection() {

	}

	/**
	 * Creates an instance of WindDirection.
	 * 
	 * @param degrees The wind direction in degrees.
	 * @param name The wind direction name.
	 */
	public WindDirection(double degrees, String name) {
		this.degrees = degrees;
		this.name = name;
	}

	/**
	 * Converts the wind direction angle to the sin of the angle.
	 * 
	 * @return The sin of the angle.
	 */
	@JsonProperty
	public double sinDeg() {
		return Math.sin(Math.toRadians(degrees));
	}



	@Override
	public void printItem(PrintWriter pw) {
		pw.println(degrees + "," + name + ',');

	}

}
