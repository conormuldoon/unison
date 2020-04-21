package eu.acclimatize.unison;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.location.Location;
import eu.acclimatize.unison.user.UserInformation;

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
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encoded = passwordEncoder.encode(TestConstant.PASSWORD);

		ItemKey itemKey0 = new ItemKey(fh0,
				new Location(TestConstant.LOCATION, new UserInformation(TestConstant.USERNAME, encoded), null));
		ItemKey itemKey1 = new ItemKey(fh1,
				new Location(TestConstant.LOCATION, new UserInformation(TestConstant.USERNAME, encoded), null));

		Assert.assertEquals(itemKey0, itemKey1);
		Assert.assertEquals(itemKey0.hashCode(), itemKey1.hashCode());

	}

	private Date fromHour() {
		return new GregorianCalendar(2019, Calendar.NOVEMBER, 4).getTime();
	}
}
