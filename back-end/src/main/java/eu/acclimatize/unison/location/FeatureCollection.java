package eu.acclimatize.unison.location;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.acclimatize.unison.Constant;

@JsonSerialize(using = FeatureCollectionSerializer.class)
public class FeatureCollection {

	private List<? extends CoordinatesSerializer> locationList;
	private PointSerializer pointSerializer;
	private static final String FEATURE_COLLECTION="FeatureCollection";
	private static final String FEATURES ="features";

	public FeatureCollection(List<? extends CoordinatesSerializer> locationList, PointSerializer pointSerializer) {
		this.locationList = locationList;
		this.pointSerializer = pointSerializer;
	}

	public void geoJsonSerialize(JsonGenerator gen) throws IOException {
		gen.writeStartObject();

		gen.writeStringField(Constant.TYPE, FEATURE_COLLECTION);
		gen.writeArrayFieldStart(FEATURES);
		for (CoordinatesSerializer l : locationList) {

			l.serialize(gen, pointSerializer);
		}
		gen.writeEndArray();
		gen.writeEndObject();

	}

}
