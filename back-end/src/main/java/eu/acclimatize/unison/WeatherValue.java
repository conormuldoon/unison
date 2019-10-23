package eu.acclimatize.unison;

import javax.persistence.Embedded;

/**
 * A class for storing non-precipitation weather values.
 * 
 *
 */
@SuppressWarnings("unused")
public class WeatherValue {
	
	// The private attributes are used by JPA when storing the data.

	private Double temperature;

	@Embedded
	private WindDirection windDirection;

	@Embedded
	private WindSpeed windSpeed;

	
	private Double humidity;

	private Double pressure;

	private Double cloudiness;

	@Embedded
	private Cloud cloud;
	private Double dewPoint;

	private Double fog;

	/**
	 * Creates an instance of WeatherValue.
	 * 
	 * @param t The temperature.
	 * @param wd The wind direction.
	 * @param ws The wind speed.
	 * @param h The humidity.
	 * @param p The pressure.
	 * @param c The cloudiness percentage.
	 * @param cd The low, medium, and high cloud percentages.
	 * @param dp The dew point level.
	 * @param fg The level of fog.
	 */
	public WeatherValue(Double t, WindDirection wd, WindSpeed ws, Double h, Double p, Double c,Cloud cd, Double dp, Double fg) {
		temperature = t;
		windDirection = wd;
		windSpeed = ws;
		humidity = h;
		pressure = p;
		cloudiness = c;
		cloud=cd;
		dewPoint = dp;
		fog = fg;
	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public WeatherValue() {

	}

}
