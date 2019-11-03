package eu.acclimatize.unison.user;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.Constant;

/**
 * 
 * A class that enables user credentials (user name and password) to be stored
 * in the database using the terminal and without launching the Spring
 * application.
 * 
 *
 */
public class UserConsole {

	public static final String CONFIG = "userinformation.cfg.xml";

	CredentialsRequester credentialsRequester;

	/**
	 * Creates and instance of UserConsole.
	 * 
	 * @param credentialsRequester Used to request and read user names and
	 *                             passwords.
	 * 
	 */
	public UserConsole(CredentialsRequester credentialsRequester) {

		this.credentialsRequester = credentialsRequester;

	}

	private void hideInfoLogs() {

		System.setProperty("org.jboss.logging.provider", "jdk");
		Logger loggerHibernate = Logger.getLogger("org.hibernate");
		loggerHibernate.setLevel(Level.WARNING);

		ch.qos.logback.classic.Logger mchangeLogger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger("com.mchange.v2");
		mchangeLogger.setLevel(ch.qos.logback.classic.Level.WARN);
	}

	private void execute(Logger logger) {
		hideInfoLogs();
		Configuration cfg = new Configuration();

		Properties properties = new Properties();
		try {
			properties.load(UserConsole.class.getResourceAsStream(Constant.PROPERTIES_FILE));
			cfg.configure(CONFIG);

			String driverClass = properties.getProperty("spring.datasource.driverClassName");
			cfg.setProperty("hibernate.connection.driver_class", driverClass);
			cfg.setProperty("hibernate.dialect", properties.getProperty("spring.jpa.properties.hibernate.dialect"));
			cfg.setProperty("hibernate.connection.url", properties.getProperty("spring.datasource.url"));
			cfg.setProperty("hibernate.connection.username", properties.getProperty("spring.datasource.username"));
			cfg.setProperty("hibernate.connection.password", properties.getProperty("spring.datasource.password"));
			cfg.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("spring.jpa.hibernate.ddl-auto"));

			UserInformation userInformation = credentialsRequester.requestUserInformation();

			storeUser(userInformation, cfg, logger);

		} catch (IOException e) {

			logger.log(Level.SEVERE, e.getMessage());

		}

	}

	/**
	 * Creates and executes a UserConsole using the system console and a bcrypt
	 * encoder.
	 * 
	 * @param args The arguments are not used.
	 * 
	 */
	public static void main(String[] args) {

		Console console = System.console();
		PrintWriter pw = new PrintWriter(System.out, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		UserConsole uc = new UserConsole(
				new CredentialsRequester(pw, br, new BCryptPasswordEncoder(), new SecureRandom()));
		console.printf("Starting\n");
		uc.execute(Logger.getLogger(UserConsole.class.getName()));
		pw.close();

		try {
			br.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private void storeUser(UserInformation user, Configuration configuration, Logger logger) {
		SessionFactory factory = null;
		Session session = null;
		try {
			factory = configuration.buildSessionFactory();
			session = factory.openSession();
			Transaction t = session.getTransaction();
			session.beginTransaction();
			session.saveOrUpdate(user);
			try {

				t.commit();
			} catch (javax.persistence.PersistenceException pe) {
				logger.log(Level.SEVERE, pe.getMessage());
			}

		} finally {
			if (factory != null) {
				if (session != null)
					session.close();
				factory.close();
			}
		}

	}

}
