package eu.acclimatize.unison.location;

import java.io.IOException;
import java.util.Optional;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eu.acclimatize.unison.user.UserInformation;
import eu.acclimatize.unison.user.UserRepository;

/**
 * A deserializer for converting a GeoJSON point feature to a {@link Location}
 * object.
 *
 */
@JsonComponent
public class LocationDeserializer extends JsonDeserializer<Location> {

	private UserRepository userRepository;

	private GeometryFactory geometryFactory;

	/**
	 * Creates an instance of LocationDeserializer.
	 * 
	 * @param userRepository  User to obtain user information for the location.
	 * @param geometryFactory Used for creating a point geometry.
	 */
	public LocationDeserializer(UserRepository userRepository, GeometryFactory geometryFactory) {
		this.userRepository = userRepository;
		this.geometryFactory = geometryFactory;
	}

	private String parseProperties(JsonParser parser) throws IOException {
		parser.nextToken();
		JsonToken propToken;
		while ((propToken = parser.nextToken()) != JsonToken.END_OBJECT) {

			if (propToken.equals(JsonToken.FIELD_NAME)) {
				String propName = parser.getCurrentName();
				if (propName.equals(LocationConstant.LOCATION_NAME)) {
					parser.nextToken();
					return parser.getValueAsString();
				}
			}
		}
		throw new LocationDeserializationException("No location name field in the properties object.");
	}

	private double[] parseGeometry(JsonParser parser) throws IOException {

		Double longitude = null, latitude = null;

		JsonToken jsonToken;
		while ((jsonToken = parser.nextToken()) != JsonToken.END_OBJECT) {

			if (jsonToken.equals(JsonToken.FIELD_NAME)) {
				String currentName = parser.getCurrentName();
				if (currentName.equals(LocationConstant.COORDINATES)) {
					parser.nextToken();
					parser.nextToken();
					longitude = parser.getDoubleValue();
					parser.nextToken();
					latitude = parser.getDoubleValue();

				}
			}
		}

		if (longitude == null) {
			throw new LocationDeserializationException("No coordinates in the geometry object.");
		}

		double[] coordinates = { longitude, latitude };
		return coordinates;

	}

	@Override
	public Location deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		String locationName = null;
		double[] coordinates = null;
		
		JsonToken jsonToken;
		while ((jsonToken = parser.nextToken()) != JsonToken.END_OBJECT) {

			if (jsonToken.equals(JsonToken.FIELD_NAME)) {
				String currentName = parser.getCurrentName();
				if (currentName.equals(LocationConstant.GEOMETRY)) {
					
					coordinates= parseGeometry(parser);

				} else if (currentName.equals(LocationConstant.PROPERTIES)) {
					
					locationName = parseProperties(parser);
				
				}
			}
		}
		
		parser.close();
		
		if (locationName == null) {
			throw new LocationDeserializationException("No properties in the GeoJSON point feature object.");
		}else if(coordinates==null) {
			throw new LocationDeserializationException("No geometry in the GeoJSON point feature object.");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUser = authentication.getName();
		Optional<UserInformation> oUser = userRepository.findById(authenticatedUser);

		return new Location(locationName, oUser.get(),
				geometryFactory.createPoint(new Coordinate(coordinates[0], coordinates[1])));


	}

}