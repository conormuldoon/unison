package eu.acclimatize.unison;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.user.UserConsole;

/**
 * Unit tests for password generator.
 */
public class UserConsoleTests {

	private final static String TEST_PASSWORD = "testPassword";

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

	/**
	 * Check that passwords are encoded correctly
	 */
	@Test
	public void testEncoded() {

		UserConsole passwordBCrypt = new UserConsole(null, new BCryptPasswordEncoder());

		String encoded = passwordBCrypt.encodePassword(TEST_PASSWORD);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		assertTrue(passwordEncoder.matches(TEST_PASSWORD, encoded));

	}

}
