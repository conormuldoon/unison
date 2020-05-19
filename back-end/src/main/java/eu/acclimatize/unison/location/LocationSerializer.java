package eu.acclimatize.unison.location;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * A component that serializes locations as GeoJSON point features. The
 * component invokes {@link Location#geoJSONSerialize(JsonGenerator)}.
 *
 */
@JsonComponent
public class LocationSerializer extends JsonSerializer<Location> {

	private WeatherLink[] weatherProperty;

	/**
	 * Creates an instance of LocationSerializer.
	 * 
	 * @param weatherProperty The weather properties for locations.
	 */
	public LocationSerializer(WeatherLink[] weatherProperty) {
		this.weatherProperty = weatherProperty;
	}

	@Override
	public void serialize(Location value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		value.geoJSONSerialize(gen, weatherProperty);
	}
}
