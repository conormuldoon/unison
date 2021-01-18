package eu.acclimatize.unison.location;

/**
 * 
 * A class for constants used in the location pacakge.
 *
 */
public final class LocationConstant {

	/**
	 * A private constructor to prevent constant from being instantiated by other
	 * classes.
	 */
	private LocationConstant() {

	}
	
	/**
	 * The GeoJSON feature collection type.
	 */
	public static final String FEATURE_COLLECTION = "FeatureCollection";

	/**
	 * The GeoJSON feature collection field name.
	 */
	public static final String FEATURES = "features";

	/**
	 * The GeoJSON feature field name.
	 */
	public static final String FEATURE = "Feature";

	/**
	 * The GeoJSON point field name.
	 */
	public static final String POINT = "Point";

	/**
	 * The GeoJSON geometry field name.
	 */
	public static final String GEOMETRY = "geometry";

	/**
	 * The GeoJSON coordinates field name.
	 */
	public static final String COORDINATES = "coordinates";

	/**
	 * The GeoJSON properties field name.
	 */
	public static final String PROPERTIES = "properties";
	
	/**
	 * The media type for GeoJSON.
	 */
	public static final String GEOJSON_MEDIA_TYPE = "application/geo+json";

}
