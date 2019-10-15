package eu.acclimatize.unison.location.postgis;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Point;

import eu.acclimatize.unison.location.GeoJsonSerializer;

/**
 * 
 * Used to convert point data stored in PostGIS to a GeoJSON format. Similar to {@link eu.acclimatize.unison.location.geodb.GeoDBPointSerializer}, 
 * but has dependencies on the com.vividsolution package.
 *
 */
public class PostGISPointSerializer extends JsonSerializer<Point> {
	
	private GeoJsonSerializer geoJsonSerializer;
	
	
	/**
	 * Creates an instance of PostGISPointSerializer.
	 */
	public PostGISPointSerializer() {
		geoJsonSerializer=new GeoJsonSerializer();
	}
	

	@Override
	public void serialize(Point point, JsonGenerator jGen, SerializerProvider provider)
			throws IOException{
		
		geoJsonSerializer.serialize(point.getX(),point.getY(),jGen);

	

	}

}
