package eu.acclimatize.unison;

import java.io.PrintWriter;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class for the wind speed in metres per second, Beaufort value, and wind
 * name.
 *
 */
public class WindSpeed implements HarmonieItem {

	@CSVHeaderItem("Speed (mps)")
	@JsonProperty
	@Column(name = "windSpeed_mps")
	private double mps;

	@CSVHeaderItem("Beaufort")
	@JsonProperty
	@Column(name = "windSpeed_beaufort")
	private int beaufort;

	@CSVHeaderItem("Name")
	@JsonProperty
	@Column(name = "windSpeed_name")
	private String name;

	/**
	 * A zero argument constructor for JPA.
	 */
	public WindSpeed() {

	}

	/**
	 * Creates an instance of WindSpeed
	 * 
	 * @param mps The wind speed in metres per second.
	 * @param beaufort The Beaufort scale value.
	 * @param name The wind speed name.
	 */
	public WindSpeed(double mps, int beaufort, String name) {
		this.mps = mps;
		this.beaufort = beaufort;
		this.name = name;
	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.println(mps + "," + beaufort + "," + name + ",");

	}

}
