package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FeatureCollectionSerializer extends  JsonSerializer<FeatureCollection>{

	@Override
	public void serialize(FeatureCollection value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		value.geoJsonSerialize(gen);
	}

}
