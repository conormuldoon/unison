package eu.acclimatize.unison.location;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contains{

	@JsonProperty
	private boolean value;

	public Contains(boolean value) {
		
		this.value = value;
	}
}
