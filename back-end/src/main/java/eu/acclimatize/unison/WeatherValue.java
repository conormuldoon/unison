package eu.acclimatize.unison;

import javax.persistence.Embedded;

@SuppressWarnings("unused")
public class WeatherValue {

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

	public WeatherValue() {

	}

}
