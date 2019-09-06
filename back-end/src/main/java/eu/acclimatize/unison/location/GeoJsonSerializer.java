package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

public class GeoJsonSerializer {

	private static final String TYPE = "type";
	private static final String POINT = "Point";
	private static final String COORDINATES = "coordinates";

	
	public void serialize(double x,double y, JsonGenerator jGen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jGen.writeStartObject();

		jGen.writeStringField(TYPE, POINT);

		jGen.writeArrayFieldStart(COORDINATES);
		jGen.writeNumber(x);
		jGen.writeNumber(y);
		jGen.writeEndArray();
		jGen.writeEndObject();

	}

}
