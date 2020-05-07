package eu.acclimatize.unison;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * A class for test constants.
 *
 */
public class TestConstant {

	/**
	 * A private constructor to prevent test constant from being instantiated by
	 * other classes.
	 **/
	private TestConstant() {

	}

	/**
	 * The initial date to retrieve data for.
	 */
	public static final Date FROM_DATE;

	/**
	 * The final date to retrieve data for.
	 */
	public static final Date TO_DATE;

	private static Date createDate(Calendar calendar, int year, int month, int dayOfMonth) {

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return calendar.getTime();
	}

	static {
		Calendar calendar = Calendar.getInstance();
		FROM_DATE = createDate(calendar, 2020, 1, 1);
		TO_DATE = createDate(calendar, 2020, 4, 21);
	}

	/**
	 * The name of the location.
	 */
	public static final String LOCATION = "UCD";

	/**
	 * The longitude coordinate of the location.
	 */
	public static final double LONGITUDE = -6.224176;

	/**
	 * The latitude coordinate of the location.
	 */
	public static final double LATITUDE = 53.308366;

	/**
	 * The test user name.
	 */
	public static final String USERNAME = "Alice";

	/**
	 * The test password.
	 */
	public static final String PASSWORD = "AlicesUnencodedPassword";

	/**
	 * The name of a user not added prior to the location and user controller tests.
	 */
	public static final String OTHER_USERNAME = "Bob";

	/**
	 * The password of a user not added prior to the location and user controller
	 * tests.
	 */
	public static final String OTHER_USER_PASSWORD = "BobsUnencodedPassword";

}
