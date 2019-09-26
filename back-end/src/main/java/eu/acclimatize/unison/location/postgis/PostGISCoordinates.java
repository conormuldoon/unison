package eu.acclimatize.unison.location.postgis;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

import eu.acclimatize.unison.location.LocationDetails;

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



	public PostGISCoordinates( Point geom, LocationDetails location) {
		this.name = name;
		this.geom = geom;
		this.location = location;
		
	}
	
	public PostGISCoordinates() {
		
	}



}
