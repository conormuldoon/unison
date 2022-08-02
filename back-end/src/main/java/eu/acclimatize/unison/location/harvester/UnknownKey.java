package eu.acclimatize.unison.location.harvester;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.location.Location;

/**
 * 
 * A composite key used for storing data for unknown weather variables.
 *
 */
public class UnknownKey implements Serializable {

	private static final long serialVersionUID = 205021704051775170L;

	private String weatherItem;
	private Date fromHour;

	@MapsId(Constant.NAME)
	@ManyToOne
	private Location location;

	/**
	 * A zero argument constructor required by JPA.
	 */
	public UnknownKey() {
		
	}
	
	/**
	 * Creates an instance of UnknownKey.
	 * 
	 * @param fromHour The hour that the data relates to.
	 * @param location The location the data was obtained for.
	 * @param weatherItem The name of the weather varialbe.	
	 */
	public UnknownKey(Date fromHour, Location location, String weatherItem) {
		this.fromHour = fromHour;
		this.location = location;
		this.weatherItem = weatherItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromHour == null) ? 0 : fromHour.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((weatherItem == null) ? 0 : weatherItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnknownKey other = (UnknownKey) obj;
		if (fromHour == null) {
			if (other.fromHour != null)
				return false;
		} else if (!fromHour.equals(other.fromHour))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (weatherItem == null) {
			if (other.weatherItem != null)
				return false;
		} else if (!weatherItem.equals(other.weatherItem))
			return false;
		return true;
	}


}
