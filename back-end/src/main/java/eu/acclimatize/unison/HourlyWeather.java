package eu.acclimatize.unison;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity

public class HourlyWeather {

	@EmbeddedId
	private ItemKey key;

	@Embedded
	private WeatherValue value;

	public HourlyWeather(ItemKey k, WeatherValue weatherValue) {

		key = k;
		value = weatherValue;
	}

	public HourlyWeather() {

	}



}
