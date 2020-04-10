package eu.acclimatize.unison.location.postgis;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.core.JsonGenerator;
import com.vividsolutions.jts.geom.Point;

import eu.acclimatize.unison.location.CoordinatesSerializer;
import eu.acclimatize.unison.location.LocationDetails;
import eu.acclimatize.unison.location.PointSerializer;

/**
 * 
 * An entity class to store coordinates for GeoDB. Similar to {@link eu.acclimatize.unison.location.geodb.GeoDBCoordinates}, 
 * but has dependencies on the com.vividsolutions package.
 *
 */
@Entity
public class PostGISCoordinates implements CoordinatesSerializer{

	@Id
	private String name;

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

	@Override
	public void serialize(JsonGenerator gen, PointSerializer pointSerializer) throws IOException {
		pointSerializer.serialize(geom.getX(), geom.getY(), location, gen);
		
	}



}
