package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.PrecipitationValue;

/**
 * A Jackson annotated class that is used to store a result row from a precipitation data query.
 *
 */
public class PrecipitationResult implements HarmonieItem {

	@JsonProperty
	private Date date;

	@JsonProperty
	private PrecipitationValue precipitation;

	/**
	 * Creates and instance of PrecipitationResult.
	 * 
	 * @param date The hour from which the results relates.
	 * @param precipitation Stores the value, minimum value, and maximum value for precipitation. The 
	 * minimum and maximum values may not be presents for some locations or in the Norwegian model.
	 */
	public PrecipitationResult(Date date, PrecipitationValue precipitation) {
		this.date = date;

		this.precipitation = precipitation;

	}

	/**
	 * Prints the title/header in CSV format.
	 * @param pw The writer the title is printed to.
	 */
	public static void printTitle(PrintWriter pw) {
		pw.print("date,");
		PrecipitationValue.printTitle(pw);

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');
		precipitation.printItem(pw);

	}

}
