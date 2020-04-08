package eu.acclimatize.unison.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

public interface CoordinatesSerializer {

	public void serialize(JsonGenerator gen, PointSerializer pointSerializer) throws IOException;
		
	
}
