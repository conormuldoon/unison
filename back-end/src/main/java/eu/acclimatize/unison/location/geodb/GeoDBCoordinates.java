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

	

	public GeoDBCoordinates(Point geom, LocationDetails location) {
		
		this.geom = geom;
		this.location = location;
		
	}
	
	public GeoDBCoordinates() {
		
	}



}
