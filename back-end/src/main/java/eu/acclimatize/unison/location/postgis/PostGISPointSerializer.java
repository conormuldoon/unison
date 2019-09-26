package eu.acclimatize.unison.location.postgis;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Point;

import eu.acclimatize.unison.location.GeoJsonSerializer;

public class PostGISPointSerializer extends JsonSerializer<Point> {
	
	GeoJsonSerializer geoJsonSerializer;
	
	
	public PostGISPointSerializer() {
		geoJsonSerializer=new GeoJsonSerializer();
	}
	

	@Override
	public void serialize(Point point, JsonGenerator jGen, SerializerProvider provider)
			throws IOException{
		
		geoJsonSerializer.serialize(point.getX(),point.getY(),jGen);

	

	}

}
