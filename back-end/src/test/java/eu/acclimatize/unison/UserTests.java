package eu.acclimatize.unison;

import static org.junit.Assert.assertNotSame;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.SecureRandom;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.user.CredentialsRequester;

/**
 * Unit tests for the user package.
 */
public class UserTests {

	/**
	 * Tests that the password encoder is invoked by the credentials requester.
	 * 
	 * @throws IOException Thrown if there is a problem closing a buffered reader or requesting user information.
	 */
	@Test
	public void testRequestData() throws IOException{

		BCryptPasswordEncoder mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);

		ByteArrayInputStream bais = new ByteArrayInputStream("conor\ny\n".getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(bais));
		PrintWriter mockWriter = Mockito.mock(PrintWriter.class);
		CredentialsRequester requester = new CredentialsRequester(mockWriter, br, mockEncoder, new SecureRandom());

		requester.requestUserInformation();
		br.close();

		Mockito.verify(mockEncoder, Mockito.times(1)).encode(Mockito.anyString());

	}

	/**
	 * Check that randomly generated passwords are different
	 */
	@Test
	public void testRandDiff() {
		CredentialsRequester requester = new CredentialsRequester(null, null, null, new SecureRandom());

		String r0 = requester.randomPassword();
		String r1 = requester.randomPassword();

		assertNotSame(r0, r1);
	}

}
