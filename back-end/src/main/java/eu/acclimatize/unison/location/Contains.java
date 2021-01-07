package eu.acclimatize.unison.location;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Used by the collection contains controller to represent whether the
 * collection contains a location or not.
 *
 */
public class Contains {

	@JsonProperty
	private boolean value;

	/**
	 * Creates an instance of Contains.
	 * 
	 * @param value The value will be true if the location is present in the
	 *              database or false otherwise.
	 */
	public Contains(boolean value) {

		this.value = value;
	}
}
