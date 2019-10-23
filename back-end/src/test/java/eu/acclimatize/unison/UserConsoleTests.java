package eu.acclimatize.unison;

import static org.junit.Assert.assertNotSame;

import java.security.SecureRandom;

import org.junit.Test;

import eu.acclimatize.unison.user.CredentialsRequester;

/**
 * Unit tests for password generator.
 */
public class UserConsoleTests {

	/**
	 * Check that randomly generated passwords are different
	 */
	@Test
	public void testRandDiff() {
		CredentialsRequester requester = new CredentialsRequester(null, null, new SecureRandom());

		String r0 = requester.randomPassword();
		String r1 = requester.randomPassword();

		assertNotSame(r0, r1);
	}

}
