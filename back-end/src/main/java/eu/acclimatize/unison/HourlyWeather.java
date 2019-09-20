package eu.acclimatize.unison;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


/**
 * 
 * A JPA entity class for storing hourly weather data.
 *
 */
@Entity
public class HourlyWeather {

	@EmbeddedId
	private ItemKey key;

	@Embedded
	private WeatherValue value;

	/**
	 * Creates an instance of HourlyWeather.
	 * 
	 * @param k The entities primary key.
	 * @param weatherValue The weather value for the hour.
	 */
	public HourlyWeather(ItemKey k, WeatherValue weatherValue) {

		key = k;
		value = weatherValue;
	}

	/**
	 * A zero argumenet constructor for JPA.
	 */
	public HourlyWeather() {

	}



}
