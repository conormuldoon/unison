package eu.acclimatize.unison.location;

import org.locationtech.jts.geom.Point;

import eu.acclimatize.unison.user.UserInformation;

public class LocationDTO {

	private String name;

	private UserInformation user;

	private Point geom;

	public LocationDTO(String name, UserInformation user, Point geom) {
		this.name = name;
		this.user = user;
		this.geom = geom;
	}

	public Location createEntity() {
		return new Location(name, user, geom);
	}
}
