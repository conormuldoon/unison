package eu.acclimatize.unison;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import eu.acclimatize.unison.location.Location;

/**
 * 
 * Tests that item key equality and hash function.
 *
 */
class ItemKeyTest {

	/**
	 * Tests that item keys with the same from hour and location are equal and have
	 * the same hash code.
	 */
	@Test
	void equalityAndHashTest() {

		Date fh0 = fromHour(4);
		Date fh1 = fromHour(4);

		ItemKey itemKey0 = new ItemKey(fh0, new Location(TestConstant.LOCATION, null, null));
		ItemKey itemKey1 = new ItemKey(fh1, new Location(TestConstant.LOCATION, null, null));

		Assertions.assertEquals(itemKey0, itemKey0);
		Assertions.assertEquals(itemKey0, itemKey1);
		Assertions.assertEquals(itemKey0.hashCode(), itemKey1.hashCode());
		

	}

	/**
	 * Tests that item keys with a different from hour and location are not equal.
	 */
	@Test
	void differentKey() {

		Date fh0 = fromHour(4);
		Date fh1 = fromHour(5);

		ItemKey itemKey0 = new ItemKey(fh0, new Location(TestConstant.LOCATION, null, null));
		ItemKey itemKey1 = new ItemKey(fh1, new Location(TestConstant.LOCATION, null, null));

		Assertions.assertNotEquals(itemKey0, itemKey1);

		ItemKey itemKey2 = new ItemKey(fh0, new Location("Other Location", null, null));

		Assertions.assertNotEquals(itemKey0, itemKey2);

		ItemKey itemKey3 = new ItemKey(fh0, null);
		Assertions.assertNotEquals(itemKey0, itemKey3);
		Assertions.assertNotEquals(itemKey0.hashCode(), itemKey3.hashCode());
		
		ItemKey itemKey4 = new ItemKey(null, new Location(TestConstant.LOCATION, null, null));
		Assertions.assertNotEquals(itemKey0, itemKey4);
		Assertions.assertNotEquals(itemKey0.hashCode(), itemKey4.hashCode());
		

	}

	private Date fromHour(int day) {
		return new GregorianCalendar(2019, Calendar.NOVEMBER, day).getTime();
	}
}
