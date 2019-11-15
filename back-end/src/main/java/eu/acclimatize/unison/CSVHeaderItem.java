package eu.acclimatize.unison;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * An annotation used in specifying CSV header text;
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVHeaderItem {

	/**
	 * Provides the value specified using the annotation or "" if the value was not
	 * specified. In the latter case, the Java variable name of the attribute will
	 * be used by {@link eu.acclimatize.unison.csvcontroller.CSVResponderConfig} in
	 * creating CSV headers.
	 * 
	 * @return The text to for an attribute to in a CSV header.
	 */

	public String value() default "";
}