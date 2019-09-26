package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * 
 * Used by spatial database serializers to serialize point data to GeoJSON data.
 *
 */
public class GeoJsonSerializer {

	private static final String TYPE = "type";
	private static final String POINT = "Point";
	private static final String COORDINATES = "coordinates";

	
	/** Writes x and y coordinates to the JSON generator in a GeoJSON format.
	 * 
	 * @param x The x coordinate of the point.
	 * @param y The y coordinate of the point.
	 * @param jGen The JsonGenerator object written to.
	 * @throws IOException Thrown if there is a problem writing to the JSON generator.
	 */
	public void serialize(double x,double y, JsonGenerator jGen)
			throws IOException {

		jGen.writeStartObject();

		jGen.writeStringField(TYPE, POINT);

		jGen.writeArrayFieldStart(COORDINATES);
		jGen.writeNumber(x);
		jGen.writeNumber(y);
		jGen.writeEndArray();
		jGen.writeEndObject();

	}

}
