package eu.acclimatize.unison.location.harvester;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * An entity for unknown weather variable data. Unknown weather variables are variables added to the harvested
 * end-point by the meteorological service post hoc.	
 *
 */
@Entity
public class UnknownWV {

	@EmbeddedId
	private UnknownKey key;

	@ElementCollection
	private Set<String> item;

	/**
	 * A zero argument constructor for JPA.
	 */
	public UnknownWV() {

	}

	/**
	 * Creates an instance of UnknownWV.
	 * 
	 * @param key The composite key for the entity.
	 * @param item The set of data related to the key	.
	 */
	public UnknownWV(UnknownKey key, Set<String> item) {
		this.key = key;
		this.item = item;
	}
}
