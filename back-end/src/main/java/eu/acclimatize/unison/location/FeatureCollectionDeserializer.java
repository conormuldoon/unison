package eu.acclimatize.unison.location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * A component deserializing GeoJSON feature collections.
 *
 */
@JsonComponent
public class FeatureCollectionDeserializer extends JsonDeserializer<FeatureCollection> {

	private LocationDeserializer locationDeserializer;

	/**
	 * Creates an instance of FeatureCollectionDeserializer.
	 * 
	 * @param locationDeserializer The deserializer used for individual locations.
	 */
	public FeatureCollectionDeserializer(LocationDeserializer locationDeserializer) {

		this.locationDeserializer = locationDeserializer;
	}

	@Override
	public FeatureCollection deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonToken jsonToken;
		List<Location> location = new ArrayList<>();
		boolean noFeatures = true;
		while ((jsonToken = parser.nextToken()) != JsonToken.END_OBJECT) {

			if (jsonToken.equals(JsonToken.FIELD_NAME)) {
				String currentName = parser.getCurrentName();
				if (currentName.equals(LocationConstant.FEATURES)) {
					noFeatures = false;
					while ((jsonToken = parser.nextToken()) != JsonToken.END_ARRAY) {
						if (jsonToken == JsonToken.START_OBJECT) {
							location.add(locationDeserializer.deserialize(parser, ctxt));
						}
					}
				}
			}
		}
		if (noFeatures) {
			throw new DeserializationException(
					"No " + LocationConstant.FEATURES + " in the GeoJSON feature collection.");
		}
		return new FeatureCollection(location);
	}

}
