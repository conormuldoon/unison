package eu.acclimatize.unison.location;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.w3c.dom.Document;

import eu.acclimatize.unison.harvester.DocumentRequestService;
import eu.acclimatize.unison.user.UserInformation;

/**
 * 
 * An entity class for storing generic (non-spatial) information related to coordinates.
 *
 */
@Entity
public class LocationDetails {

	@Id
	private String name;

	private String uri;

	@ManyToOne
	private UserInformation user;

	/**
	 * Creates an instance of LocationDetails.
	 * 
	 * @param name The name of the location.
	 * @param uri The URI where data related to the location will be obtained from.
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

	/** Requests XML data from the {@link eu.acclimatize.unison.harvester.DocumentRequestService}
	 * 
	 * @param drs The service that requests data from a HARMONIE-AROME API.
	 * @return An Optional contain an XML document if the request was successful and an empty Optional otherwise.
	 */
	public Optional<Document> requestData(DocumentRequestService drs) {

		return drs.documentForURI(uri);

	}
	

}