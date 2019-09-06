package eu.acclimatize.unison;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import eu.acclimatize.unison.location.LocationDetails;

@Embeddable // Required as is Hibernate Composite-id class
public class ItemKey implements Serializable {

	final static private String NAME = "name";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2386171964847399660L;

	private Date fromHour;

	@MapsId(NAME)
	@ManyToOne
	private LocationDetails location;

	public ItemKey(Date fromHour, LocationDetails location) {
		this.fromHour = fromHour;
		this.location = location;
	}
	
	public ItemKey() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fromHour.hashCode();
		result = prime * result + location.hashCode();
		return result;
	}

	// Required as is Hibernate Composite-id class
	@Override
	public boolean equals(Object obj) {

		ItemKey other = (ItemKey) obj;
		return fromHour.equals(other.fromHour) && location.equals(other.location);

	}



}
