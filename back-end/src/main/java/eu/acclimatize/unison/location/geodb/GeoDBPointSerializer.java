package eu.acclimatize.unison.location.geodb;

import java.io.IOException;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.acclimatize.unison.location.GeoJsonSerializer;

public class GeoDBPointSerializer extends JsonSerializer<Point> {
	
	GeoJsonSerializer geoJsonSerializer;
	
	
	public GeoDBPointSerializer() {
		geoJsonSerializer=new GeoJsonSerializer();
	}
	

	@Override
	public void serialize(Point point, JsonGenerator jGen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
		geoJsonSerializer.serialize(point.getX(),point.getY(),jGen,provider);

	

	}

}
