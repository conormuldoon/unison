package eu.acclimatize.unison.location.geodb;

import java.io.IOException;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.acclimatize.unison.location.GeoJsonSerializer;

/**
 * 
 * Used to convert point data stored in GeoDB to a GeoJSON format. Similar to {@link eu.acclimatize.unison.location.postgis.PostGISPointSerializer}, 
 * but has dependencies on the org.locationtech package.
 *
 */
public class GeoDBPointSerializer extends JsonSerializer<Point> {
	
	private GeoJsonSerializer geoJsonSerializer;
	
	/**
	 * Creates an instance of GeoDBPointSerializer.
	 */
	public GeoDBPointSerializer() {
		geoJsonSerializer=new GeoJsonSerializer();
	}
	

	@Override
	public void serialize(Point point, JsonGenerator jGen, SerializerProvider provider)
			throws IOException{
		
		geoJsonSerializer.serialize(point.getX(),point.getY(),jGen);

	

	}

}
