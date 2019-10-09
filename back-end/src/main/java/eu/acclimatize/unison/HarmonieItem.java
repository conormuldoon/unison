package eu.acclimatize.unison;

import java.io.PrintWriter;

/**
 * An interface that should be implemented by classes that will be printed in a Comma Separated Values (CSV) format.
 *
 */
public interface HarmonieItem {
	
	/**
	 * Prints the body in CSV format.
	 * @param pw The writer the body is printed to.
	 */
	public void printItem(PrintWriter pw);
}
