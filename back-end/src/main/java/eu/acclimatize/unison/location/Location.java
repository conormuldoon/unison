package eu.acclimatize.unison.location;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.parsers.DocumentBuilder;

import org.locationtech.jts.geom.Point;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonGenerator;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.user.UserInformation;

/**
 * 
 * An entity class for storing generic (non-spatial) information related to
 * coordinates.
 *
 */
@Entity
public class Location implements Serializable {

	private static final String POINT = "Point";
	private static final String COORDINATES = "coordinates";
	private static final String PROPERTIES = "properties";
	private static final String GEOMETRY = "geometry";
	private static final String FEATURE = "Feature";
	private static final String FN_NAME = "name";

	private static final long serialVersionUID = 1771422791257298902L;

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

	/**
	 * Requests XML data for the longitude and latitude coordinates from the HARMONIE-AROME API.
	 * 
	 * @param uri             The URL template for a HARMONIE-AROME API specified by
	 *                        app.uri in the application properties file.
	 * @param documentBuilder A builder used to parse the XML document.
	 * @return The an XML document obtained.
	 * @throws IOException              Thrown if there is an I/O error when
	 *                                  connecting to the HARMONIE-AROME API.
	 * @throws SAXException             Thrown if there is an error in parsing the
	 *                                  document.
	 * @throws LocationRequestException Thrown when the generated XML for the
	 *                                  location was not found.
	 */
	public Document requestDocument(String uri, DocumentBuilder documentBuilder,Logger logger) throws SAXException, IOException, LocationRequestException {

		String locURI = String.format(uri, geom.getY(), geom.getX()); // Change to just return this string, maybe.
		logger.log(Level.INFO, () -> "Requesting data for " + name + " from " + locURI + '.');
		try {
			return documentBuilder.parse(locURI);
		} catch (FileNotFoundException e) {
			throw new LocationRequestException(
					"Problem obtaining document for " + name + ". The gnerated XML was not found.");
		}

	}

	/**
	 * Serializes the location in a GeoJSON format.
	 * 
	 * @param gen The generator object written to.
	 * @throws IOException Thrown if there is an I/O error while serializing.
	 */
	public void serialize(JsonGenerator gen) throws IOException {
		gen.writeStartObject();

		gen.writeStringField(Constant.TYPE, FEATURE);
		
		gen.writeFieldName(GEOMETRY);
		gen.writeStartObject();
		gen.writeStringField(Constant.TYPE, POINT);
		gen.writeArrayFieldStart(COORDINATES);
		gen.writeNumber(geom.getX());
		gen.writeNumber(geom.getY());
		gen.writeEndArray();
		gen.writeEndObject();

		gen.writeFieldName(PROPERTIES);
		gen.writeStartObject();
		gen.writeStringField(FN_NAME, name);
		gen.writeEndObject();

		gen.writeEndObject();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}