package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * Jackson uses an instance of this class when serializing a location if not
 * serializing the location as feature in a feature collection. The instance
 * invokes {@link Location#geoJSONSerialize(JsonGenerator)}.
 *
 */
public class LocationSerializer extends JsonSerializer<Location> {

	@Override
	public void serialize(Location value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		value.geoJSONSerialize(gen);
	}
}
