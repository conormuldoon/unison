package eu.acclimatize.unison.location;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.w3c.dom.Document;

import eu.acclimatize.unison.harvester.DocumentRequestService;
import eu.acclimatize.unison.user.UserInformation;

@Entity
public class LocationDetails {

	@Id
	String name;

	private String uri;

	@ManyToOne
	UserInformation user;

	public LocationDetails(String name, String uri, UserInformation user) {

		this.name = name;
		this.uri = uri;
		this.user = user;

	}

	public LocationDetails() {

	}

	public Optional<Document> requestData(DocumentRequestService drs) {

		return drs.documentForURI(uri);

	}
	

}