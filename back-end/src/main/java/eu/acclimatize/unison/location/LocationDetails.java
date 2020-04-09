package eu.acclimatize.unison.location;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.w3c.dom.Document;

import eu.acclimatize.unison.harvester.DocumentRequestException;
import eu.acclimatize.unison.harvester.DocumentRequestService;
import eu.acclimatize.unison.user.UserInformation;

/**
 * 
 * An entity class for storing generic (non-spatial) information related to
 * coordinates.
 *
 */
@Entity
public class LocationDetails implements Serializable {

	private static final long serialVersionUID = 1771422791257298902L;

	@Id
	private String name;

	private String uri;

	@ManyToOne
	private UserInformation user;

	/**
	 * Creates an instance of LocationDetails.
	 * 
	 * @param name The name of the location.
	 * @param uri  The URI where data related to the location will be obtained from.
	 * @param user Information related to the user that added the location.
	 */
	public LocationDetails(String name, String uri, UserInformation user) {

		this.name = name;
		this.uri = uri;
		this.user = user;

	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public LocationDetails() {

	}

	/**
	 * Requests XML data from the
	 * {@link eu.acclimatize.unison.harvester.DocumentRequestService}
	 * 
	 * @param drs The service that requests data from a HARMONIE-AROME API.
	 * @return An Optional contain an XML document if the request was successful and
	 *         an empty Optional otherwise.
	 * @throws DocumentRequestException Thrown when the generated XML for the location was not found.
	 */
	public Optional<Document> requestData(DocumentRequestService drs) throws DocumentRequestException {

		return drs.documentForURI(name, uri);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		LocationDetails other = (LocationDetails) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}