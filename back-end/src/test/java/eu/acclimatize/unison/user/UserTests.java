package eu.acclimatize.unison.user;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.concurrent.Executor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.TestConstant;

/**
 * Non-controller unit tests for the user package.
 */
class UserTests {

	private ByteArrayInputStream mockInputStream() {

		return new ByteArrayInputStream((TestConstant.USERNAME + "\ny\n").getBytes());

	}

	/**
	 * Tests that the password encoder is invoked by the credentials requester.
	 * 
	 * @throws IOException Thrown if there is a problem closing a buffered reader or
	 *                     requesting user information.
	 */
	@Test
	void testRequestData() throws IOException {

		BCryptPasswordEncoder mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);

		ByteArrayInputStream bais = mockInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(bais));
		PrintWriter mockWriter = Mockito.mock(PrintWriter.class);
		CredentialsRequester requester = new CredentialsRequester(mockWriter, br, mockEncoder, new SecureRandom());
		requester.requestUserInformation();
		br.close();

		Mockito.verify(mockEncoder, Mockito.times(1)).encode(Mockito.anyString());

	}

	/**
	 * Tests that user data is committed using a transaction.
	 * 
	 * @throws IOException Thrown if there is a problem closing a buffered reader or
	 *                     requesting user information.
	 */
	@Test
	void testUserHibernateStore() throws IOException {

		Configuration configuration = Mockito.mock(Configuration.class);
		SessionFactory factory = Mockito.mock(SessionFactory.class);
		Mockito.when(configuration.buildSessionFactory()).thenReturn(factory);
		Session session = Mockito.mock(Session.class);
		Mockito.when(factory.openSession()).thenReturn(session);
		Transaction transaction = Mockito.mock(Transaction.class);
		Mockito.when(session.getTransaction()).thenReturn(transaction);

		BCryptPasswordEncoder mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);
		ByteArrayInputStream bais = mockInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(bais));
		PrintWriter mockWriter = Mockito.mock(PrintWriter.class);
		CredentialsRequester requester = new CredentialsRequester(mockWriter, br, mockEncoder, new SecureRandom());
		UserHibernateStore uhs = new UserHibernateStore(requester, null);
		uhs.hideInfoLogs();
		uhs.execute(configuration);
		br.close();

		Mockito.verify(transaction, Mockito.times(1)).commit();
	}

	/**
	 * Tests that a runnable task is passed to the executor.
	 */
	@Test
	void testTaskSubmitted() {
		UserRepository userRepository = Mockito.mock(UserRepository.class);

		Executor executor = Mockito.mock(Executor.class);
		UserReadyEventListener eventListener = new UserReadyEventListener(userRepository, null, true, null, null,
				executor, null);
		eventListener.initialUser();
		Mockito.verify(executor, Mockito.times(1)).execute(Mockito.any(Runnable.class));

	}

	/**
	 * Test that the initial user credentials are saved in the user repository.
	 */
	@Test
	void testInitiaUser() {

		Executor executor = new Executor() { // Runs a runnable task on the current thread.

			@Override
			public void execute(Runnable r) {
				r.run();

			}
		};

		UserRepository userRepository = Mockito.mock(UserRepository.class);

		UserReadyEventListener eventListener = new UserReadyEventListener(userRepository, null, true, null, null,
				executor, new BCryptPasswordEncoder());
		System.setIn(mockInputStream());
		eventListener.initialUser();
		Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserInformation.class));
	}

	/**
	 * Check that randomly generated passwords are different
	 */
	@Test
	void testRandDiff() {
		CredentialsRequester requester = new CredentialsRequester(null, null, null, new SecureRandom());

		String r0 = requester.randomPassword();
		String r1 = requester.randomPassword();

		Assertions.assertNotEquals(r0, r1);
	}

	/**
	 * Tests for UserInformation equality and that equal instances have the
	 * same hash codes.
	 */
	@Test
	void equalityAndHashTest() {

		UserInformation ui0 = new UserInformation(TestConstant.USERNAME, "");
		UserInformation ui1 = new UserInformation(TestConstant.USERNAME, "");

		Assertions.assertEquals(ui0, ui0);
		Assertions.assertEquals(ui0, ui1);
		Assertions.assertEquals(ui0.hashCode(), ui1.hashCode());

	}

	/**
	 * Tests that UserInformation instances with different attributes are not equal
	 * and have different hash codes.
	 */
	@Test
	void differenceUserInfo() {
		UserInformation ui0 = new UserInformation(TestConstant.USERNAME, "");
		UserInformation ui1 = new UserInformation(TestConstant.OTHER_USERNAME, "");

		Assertions.assertNotEquals(ui0, ui1);

		UserInformation ui2 = new UserInformation(TestConstant.USERNAME, " ");
		Assertions.assertNotEquals(ui0, ui2);

		UserInformation ui3 = new UserInformation(TestConstant.USERNAME, null);

		Assertions.assertNotEquals(ui0, ui3);

		UserInformation ui4 = new UserInformation(null, "");
		Assertions.assertNotEquals(ui0, ui4);

	}

}
