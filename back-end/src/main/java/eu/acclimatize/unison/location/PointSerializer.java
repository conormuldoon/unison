package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.Constant;

/**
 * 
 * Used by {@link FeatureCollection} to serialize point data to GeoJSON data.
 *
 */
public class PointSerializer {

	private static final String POINT = "Point";
	private static final String COORDINATES = "coordinates";
	private static final String PROPERTIES = "properties";
	private static final String NAME = "name";
	private static final String GEOMETRY = "geometry";
	private static final String FEATURE = "Feature";

	
	/**
	 *  Writes x and y coordinates and location to the JSON generator in a GeoJSON format.
	 *  
	 * @param x    The x coordinate of the point.
	 * @param y    The y coordinate of the point.
	 * @param name The name of the location.
	 * @param gen  The generator object written to.
	 * @throws IOException Thrown if there is an I/O error while serializing.
	 */
	public void serialize(double x, double y, String name, JsonGenerator gen) throws IOException {
		gen.writeStartObject();

		gen.writeFieldName(GEOMETRY);
		gen.writeStartObject();
		gen.writeStringField(Constant.TYPE, POINT);
		gen.writeArrayFieldStart(COORDINATES);
		gen.writeNumber(x);
		gen.writeNumber(y);
		gen.writeEndArray();
		gen.writeEndObject();

		gen.writeFieldName(PROPERTIES);
		gen.writeStartObject();
		gen.writeStringField(NAME, name);
		gen.writeEndObject();

		gen.writeStringField(Constant.TYPE, FEATURE);

		gen.writeEndObject();

	}

}