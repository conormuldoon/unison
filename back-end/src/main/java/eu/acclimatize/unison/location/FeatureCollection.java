package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.Constant;


/**
 * 
 * A class for representing a GeoJSON feature collection.
 *
 */

public class FeatureCollection{

	private static final String FEATURE_COLLECTION = "FeatureCollection";

	private Iterable<Location> features;

	/**
	 * Creates an instance of FeatureCollection.
	 * 
	 * @param features The locations within the collection.
	 */
	public FeatureCollection(Iterable<Location> features) {
		this.features = features;
	}

	/**
	 * Serializes the feature collection in a GeoJSON format.
	 * 
	 * @param gen             Used in the serialization process.
	 * @throws IOException Thrown if there if there is an I/O error when
	 *                     serializing.
	 */
	public void geoJSONSerialize(JsonGenerator gen) throws IOException {
		gen.writeStartObject();

		gen.writeStringField(Constant.TYPE, FEATURE_COLLECTION);
		gen.writeArrayFieldStart(LocationConstant.FEATURES);

		for (Location l : features) {
			l.geoJSONSerialize(gen);
		}
		gen.writeEndArray();
		gen.writeEndObject();

	}

}
