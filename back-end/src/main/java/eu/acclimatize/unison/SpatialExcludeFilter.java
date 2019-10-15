package eu.acclimatize.unison;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * A filter to exclude the injection of classes based on the 'exclude.spatial.regex' regular expression defined within the application properties file.
 *
 */
public class SpatialExcludeFilter implements TypeFilter {

	private String exclude;
	final private static String EX_SPATIAL_REGEX = "exclude.spatial.regex";

	/**
	 * Creates and instance of SpatialExcludeFilter.
	 * 
	 * @throws IOException Will be thrown if there is a problem reading from the properties file.
	 */
	public SpatialExcludeFilter() throws IOException {

		// Can't use @Value annotation for exclude as method invoked by component scan
		// prior to @Value inject, so must read from properties file directly.
		Properties properties = new Properties();
		properties.load(SpatialExcludeFilter.class.getResourceAsStream(Constant.PROPERTIES_FILE));
		exclude = properties.getProperty(EX_SPATIAL_REGEX);
	}

	@Override
	public boolean match(MetadataReader reader, MetadataReaderFactory factory) throws IOException {

		return reader.getClassMetadata().getClassName().matches(exclude);

	}

}
