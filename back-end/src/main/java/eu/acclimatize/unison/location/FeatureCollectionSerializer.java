package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * Jackson uses an instance of this class when serializing a GeoJSON feature collection. The instance
 * invokes {@link FeatureCollection#geoJSONSerialize(JsonGenerator)}.
 *
 */
public class FeatureCollectionSerializer extends JsonSerializer<FeatureCollection>{

	@Override
	public void serialize(FeatureCollection value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		value.geoJSONSerialize(gen);	
	}
}
