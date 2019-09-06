package eu.acclimatize.unison;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity
public class HourlyPrecipitation {

	@EmbeddedId
	private ItemKey key;

	@Embedded
	private PrecipitationValue value;

	public HourlyPrecipitation(ItemKey key, PrecipitationValue precipitationValue) {

		this.key = key;
		value = precipitationValue;
	}

	public HourlyPrecipitation() {
		
	}
	

}
