package eu.acclimatize.unison.location;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.Constant;

/**
 * 
 * A class for representing a GeoJSON feature collection.
 *
 */

public class FeatureCollection {

	private static final String FEATURE_COLLECTION = "FeatureCollection";

	private List<Location> locationList;

	/**
	 * Creates an instance of FeatureCollection.
	 * 
	 * @param locationList A list of features.
	 */
	public FeatureCollection(List<Location> locationList) {
		this.locationList = locationList;
	}

	/**
	 * Serializes the feature collection in a GeoJSON format.
	 * 
	 * @param gen             Used in the serialization process.
	 * @param weatherProperty
	 * @throws IOException Thrown if there if there is an I/O error when
	 *                     serializing.
	 */
	public void geoJSONSerialize(JsonGenerator gen, WeatherProperty[] weatherProperty) throws IOException {
		gen.writeStartObject();

		gen.writeStringField(Constant.TYPE, FEATURE_COLLECTION);
		gen.writeArrayFieldStart(LocationConstant.FEATURES);

		for (Location l : locationList) {
			l.geoJSONSerialize(gen, weatherProperty);
		}
		gen.writeEndArray();
		gen.writeEndObject();

	}

}
