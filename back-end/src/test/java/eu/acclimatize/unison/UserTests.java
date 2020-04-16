package eu.acclimatize.unison;

import static org.junit.Assert.assertNotSame;

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
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.user.CredentialsRequester;
import eu.acclimatize.unison.user.UserHibernateStore;
import eu.acclimatize.unison.user.UserInformation;
import eu.acclimatize.unison.user.UserRepository;
import eu.acclimatize.unison.user.UserService;

/**
 * Unit tests for the user package.
 */
public class UserTests {

	/**
	 * Tests that the password encoder is invoked by the credentials requester.
	 * 
	 * @throws IOException Thrown if there is a problem closing a buffered reader or
	 *                     requesting user information.
	 */
	@Test
	public void testRequestData() throws IOException {

		BCryptPasswordEncoder mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);

		ByteArrayInputStream bais = mockInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(bais));
		PrintWriter mockWriter = Mockito.mock(PrintWriter.class);
		CredentialsRequester requester = new CredentialsRequester(mockWriter, br, mockEncoder, new SecureRandom());
		requester.requestUserInformation();
		br.close();

		Mockito.verify(mockEncoder, Mockito.times(1)).encode(Mockito.anyString());

	}

	private ByteArrayInputStream mockInputStream() {
		return new ByteArrayInputStream("conor\ny\n".getBytes());

	}

	/**
	 * Tests that a runnable task is passed to the executor.
	 */
	@Test
	public void testTaskSubmitted() {
		UserRepository userRepository = Mockito.mock(UserRepository.class);
		
		Executor executor = Mockito.mock(Executor.class);
		UserService userService = new UserService(userRepository, null, null, true, null, null, executor);
		
		userService.initialUser();
		Mockito.verify(executor, Mockito.times(1)).execute(Mockito.any(Runnable.class));

	}

	/**
	 * Test that the initial user credentials are save in the user repository.
	 */
	@Test
	public void testInitiaUser() {

		Executor executor = new Executor() { // Runs a runnable task on the current thread.

			@Override
			public void execute(Runnable r) {
				r.run();

			}
		};
		
		UserRepository userRepository = Mockito.mock(UserRepository.class);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserService userService = new UserService(userRepository, null, passwordEncoder, true, null, null, executor);
		System.setIn(mockInputStream());
		userService.initialUser();
		Mockito.verify(userRepository,Mockito.times(1)).save(Mockito.any(UserInformation.class));
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

	/**
	 * Tests that user data is committed using a transaction.
	 * 
	 * @throws IOException Thrown if there is a problem closing a buffered reader or
	 *                     requesting user information.
	 */
	@Test
	public void testUserHibernateStore() throws IOException {
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

}
