package eu.acclimatize.unison.location.harvester;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class UnknownWV {

	@EmbeddedId
	private UnknownKey key;

	@ElementCollection
	private Set<String> item;

	public UnknownWV() {

	}

	public UnknownWV(UnknownKey key, Set<String> item) {
		this.key = key;
		this.item = item;
	}
}
