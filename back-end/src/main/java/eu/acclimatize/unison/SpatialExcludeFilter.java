package eu.acclimatize.unison;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class SpatialExcludeFilter implements TypeFilter {

	String exclude;
	final private static String EX_SPATIAL_REGEX = "exclude.spatial.regex";

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
