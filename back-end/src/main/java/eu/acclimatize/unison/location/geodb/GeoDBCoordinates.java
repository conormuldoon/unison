package eu.acclimatize.unison.location.geodb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.acclimatize.unison.location.LocationDetails;

/**
 * 
 * An entity class to store coordinates for GeoDB. Similar to {@link eu.acclimatize.unison.location.postgis.PostGISCoordinates}, 
 * but has dependencies on the org.locationtech package.
 *
 */
@Entity
public class GeoDBCoordinates {

	@Id
	@JsonProperty
	private String name;

	@JsonProperty
	@JsonSerialize(using = GeoDBPointSerializer.class)
	@Lob
	private Point geom;

	@OneToOne
	@MapsId
	private LocationDetails location;

	

	/**
	 * Creates an instance of GeoDBCoordinates. The primary key of the entity
	 * is mapped by the name attribute of {@link eu.acclimatize.unison.location.LocationDetails}.
	 * 
	 * @param geom A spatial point geometry used when storing data in GeoDB. 
	 * @param location Information related to the point.
	 */
	public GeoDBCoordinates(Point geom, LocationDetails location) {
		
		this.geom = geom;
		this.location = location;
		
	}
	
	/**
	 * A zero argument constructor of JPA.
	 */
	public GeoDBCoordinates() {
		
	}



}
