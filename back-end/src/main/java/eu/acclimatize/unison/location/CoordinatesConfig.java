package eu.acclimatize.unison.location;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

@Configuration
public class CoordinatesConfig {

	@Bean
	public Sort sort() {
		return new Sort(Sort.Direction.ASC, "name");
	}
}
