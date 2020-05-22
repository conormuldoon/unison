package eu.acclimatize.unison.location;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * A component for serializing GeoJSON feature collections. The component
 * invokes {@link FeatureCollection#geoJSONSerialize(JsonGenerator)}.
 *
 */
@JsonComponent
public class FeatureCollectionSerializer extends JsonSerializer<FeatureCollection> {


	@Override
	public void serialize(FeatureCollection value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {

		value.geoJSONSerialize(gen);
	}
}
