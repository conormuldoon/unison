package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * Class that implement this interface represent GeoJSON features that have a
 * geometry type of Point.
 */
public interface PointFeature {

	/**
	 * Serializes coordinates and associated properties in a GeoJSON format.
	 * 
	 * @param gen The generator object written to.
	 * @param pointSerializer accepts longitude and latitude values in serializing coordinates.
	 * @throws IOException Thrown if there is an I/O error while serializing.
	 */
	public void serialize(JsonGenerator gen, PointFeatureSerializer pointSerializer) throws IOException;

}
