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

	private Double globalRadiation;

	private Double gust;

	/**
	 * Creates an instance of WeatherValue.
	 * 
	 * @param t    The temperature.
	 * @param wd   The wind direction.
	 * @param ws   The wind speed.
	 * @param h    The humidity.
	 * @param p    The pressure.
	 * @param c    The cloudiness percentage.
	 * @param cd   The low, medium, and high cloud percentages.
	 * @param dp   The dew point level.
	 * @param fg   The level of fog.
	 * @param gr   The global radiation.
	 * @param gust The wind gust.
	 */
	public WeatherValue(Double t, WindDirection wd, WindSpeed ws, Double h, Double p, Double c, Cloud cd, Double dp,
			Double fg, Double gr, Double gust) {
		temperature = t;
		windDirection = wd;
		windSpeed = ws;
		humidity = h;
		pressure = p;
		cloudiness = c;
		cloud = cd;
		dewPoint = dp;
		fog = fg;
		globalRadiation = gr;
		this.gust = gust;

	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public WeatherValue() {

	}

}
