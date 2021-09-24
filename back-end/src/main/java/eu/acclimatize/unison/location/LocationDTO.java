package eu.acclimatize.unison.location;

import org.locationtech.jts.geom.Point;

import eu.acclimatize.unison.user.UserInformation;

/**
 * A DTO for receiving location data. Data must be sent by the client in a
 * GeoJSON format. The GeoJSON is deserialized by the
 * {@link LocationDeserializer}.
 *
 */
public class LocationDTO {

	private String name;

	private UserInformation user;

	private Point geom;

	/**
	 * Creates an instance of LocationDTO.
	 * 
	 * @param name The name of the location.
	 * @param user The information of the user that added the location. When
	 *             creating an instance of the DTO the {@link LocationDeserializer}
	 *             uses the database and the name of the authenticated user to
	 *             obtain the user information, as the user information is not
	 *             transmitted in the request body by the client.
	 * @param geom The point coordinates of the location.
	 */
	public LocationDTO(String name, UserInformation user, Point geom) {
		this.name = name;
		this.user = user;
		this.geom = geom;
	}

	/**
	 * Creates a JPA entity for the location.
	 * 
	 * @return The entity created.
	 */
	public Location createEntity() {
		return new Location(name, user, geom);
	}
}
