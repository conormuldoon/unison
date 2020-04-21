package eu.acclimatize.unison;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import eu.acclimatize.unison.location.Location;

/**
 * 
 * Tests that item key equality and hash function.
 *
 */
public class ItemKeyTest {

	/**
	 * Tests that item keys with the same from hour and location are equal and have
	 * the same hash code.
	 */
	@Test
	public void equalityAndHashTest() {

		Date fh0 = fromHour();
		Date fh1 = fromHour();
		
		ItemKey itemKey0 = new ItemKey(fh0,
				new Location(TestConstant.LOCATION, null, null));
		ItemKey itemKey1 = new ItemKey(fh1,
				new Location(TestConstant.LOCATION, null, null));

		Assert.assertEquals(itemKey0, itemKey1);
		Assert.assertEquals(itemKey0.hashCode(), itemKey1.hashCode());

	}

	private Date fromHour() {
		return new GregorianCalendar(2019, Calendar.NOVEMBER, 4).getTime();
	}
}
