package eu.acclimatize.unison.location;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.OwnedItem;
import eu.acclimatize.unison.user.UserInformation;

/**
 * 
 * An entity class for storing generic (non-spatial) information related to
 * coordinates.
 *
 */

@Entity
public class Location implements OwnedItem, Serializable {

	private static final long serialVersionUID = 1771422791257298902L;

	private static final String LINKS = "links";

	@Id
	private String name;

	@ManyToOne
	private UserInformation user;

	private Point geom;

	/**
	 * Creates an instance of Location.
	 * 
	 * @param name The name of the location.
	 * @param user Information related to the user that added the location.
	 * @param geom The point coordinates for the location.
	 */
	public Location(String name, UserInformation user, Point geom) {

		this.name = name;
		this.user = user;
		this.geom = geom;

	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public Location() {

	}

	public Optional<Location> findCurrent(LocationRepository locationRepository) {
		return locationRepository.findById(name);
	}

	@Override
	public boolean hasOwner(String ownerName) {
		return user.hasOwner(ownerName);
	}

	public String replaceVariables(String template) {

		// http://metwdb-openaccess.ichec.ie uses a semicolon delimiter rather than an
		// ampersand so can't use a UriTemplate.
		template = template.replace("{latitude}", String.valueOf(geom.getY()));
		return template.replace("{longitude}", String.valueOf(geom.getX()));
	}

	/**
	 * Serializes the location in a GeoJSON format.
	 * 
	 * @param gen             The generator object written to.
	 * @param weatherProperty
	 * @throws IOException Thrown if there is an I/O error while serializing.
	 */
	public void geoJSONSerialize(JsonGenerator gen, WeatherProperty[] weatherProperty) throws IOException {

		gen.writeStartObject();

		gen.writeStringField(Constant.TYPE, LocationConstant.FEATURE);

		gen.writeFieldName(LocationConstant.GEOMETRY);

		gen.writeStartObject();
		gen.writeStringField(Constant.TYPE, LocationConstant.POINT);
		gen.writeArrayFieldStart(LocationConstant.COORDINATES);
		gen.writeNumber(geom.getX());
		gen.writeNumber(geom.getY());
		gen.writeEndArray();
		gen.writeEndObject();

		gen.writeFieldName(LocationConstant.PROPERTIES);
		gen.writeStartObject();
		gen.writeStringField(Constant.LOCATION_NAME, name);
		gen.writeFieldName(LINKS);
		gen.writeStartObject();
		String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
		for (WeatherProperty wp : weatherProperty) {
			wp.write(gen, encodedName);
		}
		gen.writeStringField(Constant.HARVEST, MappingConstant.LOCATION+"/"+encodedName+"/"+Constant.HARVEST);
		gen.writeEndObject();
		gen.writeEndObject();
		gen.writeEndObject();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((geom == null) ? 0 : geom.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (geom == null) {
			if (other.geom != null)
				return false;
		} else if (!geom.equals(other.geom))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}