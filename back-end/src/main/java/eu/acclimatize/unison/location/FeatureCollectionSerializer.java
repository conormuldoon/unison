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

	private WeatherProperty[] weatherProperty;

	/**
	 * Creates an instance of FeatureCollectionSerializer.
	 * 
	 * @param weatherProperty The weather properties for locations.
	 */
	public FeatureCollectionSerializer(WeatherProperty[] weatherProperty) {
		this.weatherProperty = weatherProperty;

	}

	@Override
	public void serialize(FeatureCollection value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {

		value.geoJSONSerialize(gen, weatherProperty);
	}
}
