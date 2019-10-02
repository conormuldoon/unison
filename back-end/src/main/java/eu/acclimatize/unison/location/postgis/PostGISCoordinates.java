package eu.acclimatize.unison.location.postgis;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

import eu.acclimatize.unison.location.LocationDetails;

/**
 * 
 * An entity class to store coordinates for GeoDB. Similar to {@link eu.acclimatize.unison.location.geodb.GeoDBCoordinates}, 
 * but has dependencies on the com.vividsolutions package.
 *
 */
@Entity
public class PostGISCoordinates {

	@Id
	@JsonProperty
	private String name;

	@JsonProperty
	@JsonSerialize(using = PostGISPointSerializer.class)
	private Point geom;

	@OneToOne
	@MapsId
	private LocationDetails location;

	/**
	 * Creates an instance of PostGISCoordinates. The primary key of the entity
	 * is mapped by the name attribute of {@link eu.acclimatize.unison.location.LocationDetails}.
	 * 
	 * @param geom A spatial point geometry used when storing data in PostGIS. 
	 * @param location Information related to the point.
	 */
	public PostGISCoordinates( Point geom, LocationDetails location) {
		
		this.geom = geom;
		this.location = location;
		
	}
	
	/**
	 * A zero argument constructor of JPA.
	 */
	public PostGISCoordinates() {
		
	}



}
