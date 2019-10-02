package eu.acclimatize.unison;

import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import eu.acclimatize.unison.user.UserConsole;

/**
 * Unit tests for password generator.
 */
public class UserConsoleTests {

	

	/**
	 * Check that randomly generated passwords are different
	 */
	@Test
	public void testRandDiff() {
		UserConsole passwordBCrypt = new UserConsole(null, null);

		String r0 = passwordBCrypt.randomPassword();
		String r1 = passwordBCrypt.randomPassword();

		assertNotSame(r0, r1);
	}

	

}
