package eu.acclimatize.unison;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * 
 * A JPA entity class for storing hourly precipitation data.
 *
 */
@Entity
public class HourlyPrecipitation {

	@EmbeddedId
	private ItemKey key;

	@Embedded
	private PrecipitationValue value;

	/**
	 * Creates an instance of HourlyPrecipitation.
	 * 
	 * @param key The entities primary key.
	 * @param precipitationValue The precipitation value for the hour.
	 */
	public HourlyPrecipitation(ItemKey key, PrecipitationValue precipitationValue) {

		this.key = key;
		value = precipitationValue;
	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public HourlyPrecipitation() {
		
	}
	

}
