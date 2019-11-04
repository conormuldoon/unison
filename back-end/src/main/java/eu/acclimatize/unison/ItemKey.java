package eu.acclimatize.unison;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import eu.acclimatize.unison.location.LocationDetails;

/**
 * 
 * Represents the primary key for hourly weather and hourly precipitation data.
 *
 */
@Embeddable // Required as is Hibernate Composite-id class
public class ItemKey implements Serializable {

	private static final String NAME = "name";

	private static final long serialVersionUID = -2386171964847399660L;

	private Date fromHour;

	@MapsId(NAME)
	@ManyToOne
	private LocationDetails location;

	/**
	 * Creates an instance of ItemKey.
	 * 
	 * @param fromHour The start time of the hour.
	 * @param location The location of the hourly weather or hourly precipitation
	 *                 data.
	 */
	public ItemKey(Date fromHour, LocationDetails location) {
		this.fromHour = fromHour;
		this.location = location;
	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public ItemKey() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromHour == null) ? 0 : fromHour.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		ItemKey other = (ItemKey) obj;
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
		return true;
	}

}
