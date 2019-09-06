package eu.acclimatize.unison.result;

import java.io.PrintWriter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.acclimatize.unison.Cloud;
import eu.acclimatize.unison.HarmonieItem;

public class CloudLevelResult implements HarmonieItem {

	@JsonProperty
	Date date;

	@JsonProperty
	Cloud cloud;

	public CloudLevelResult(Date date, Cloud cloud) {
		this.date = date;
		this.cloud = cloud;
	}

	public CloudLevelResult() {
		cloud=new Cloud();
	}

	@Override
	public void printTitle(PrintWriter pw) {
		pw.print("date,");
		cloud.printTitle(pw);

	}

	@Override
	public void printItem(PrintWriter pw) {
		pw.print(date.toString() + ',');
		cloud.printItem(pw);

	}
}
