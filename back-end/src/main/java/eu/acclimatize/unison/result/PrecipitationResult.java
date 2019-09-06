package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.HarmonieItem;
import eu.acclimatize.unison.PrecipitationValue;

public class PrecipitationResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	PrecipitationValue precipitation;

	public PrecipitationResult(Date date, PrecipitationValue precipitation) {
		this.date = date;

		this.precipitation = precipitation;

	}

	public PrecipitationResult() {
		precipitation = new PrecipitationValue();
	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.print("date,");
		precipitation.printTitle(pw);

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');
		precipitation.printItem(pw);

	}

}
