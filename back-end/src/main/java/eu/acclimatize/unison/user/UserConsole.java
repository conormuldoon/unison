package eu.acclimatize.unison.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.logging.Logger;

import org.hibernate.cfg.Configuration;

import eu.acclimatize.unison.UnisonSecurityConfig;

/**
 * A command line tool that enables new users to be added to the database or
 * existing users' password to be updated. A class that enables user credentials
 * (user name and password) to be stored in the database using the terminal and
 * without launching the Spring application. Hibernate is configured using
 * properties in the application.properties file to ensure the tool is
 * configured the same as Unison Spring Boot application.
 * 
 *
 */
public final class UserConsole {

	private UserConsole() {

	}

	/**
	 * Creates a {@link CredentialsRequester} to request credentials from a user and
	 * a {@link UserHibernateStore} to save the received data using Hibernate.
	 * 
	 * @param args The arguments are not used.
	 * @throws IOException 
	 * 
	 */
	public static void main(String[] args) throws IOException {

		PrintWriter pw = new PrintWriter(System.out, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Logger logger = Logger.getLogger(UserConsole.class.getName());
		CredentialsRequester credentialsRequester = new CredentialsRequester(pw, br,
				new UnisonSecurityConfig(null).passwordEncoder(), new SecureRandom());

		UserHibernateStore uhs = new UserHibernateStore(credentialsRequester, logger);
		System.out.println("Starting");

		uhs.hideInfoLogs();
		uhs.execute(new Configuration());
		pw.close();

		br.close();
		

	}

}
