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

	private CredentialsRequester credentialsRequester;

	private Logger logger;

	/**
	 * Creates and instance of UserConsole.
	 * 
	 * @param credentialsRequester Used to request and read user names and
	 *                             passwords.
	 * @param logger               Used for logging errors.
	 * 
	 */
	public UserConsole(CredentialsRequester credentialsRequester, Logger logger) {

		this.credentialsRequester = credentialsRequester;
		this.logger = logger;

	}

	/**
	 * Hides information logs.
	 */
	public void hideInfoLogs() {
		System.setProperty("org.jboss.logging.provider", "jdk");
		Logger loggerHibernate = Logger.getLogger("org.hibernate");
		loggerHibernate.setLevel(Level.WARNING);

		ch.qos.logback.classic.Logger mchangeLogger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger("com.mchange.v2");
		mchangeLogger.setLevel(ch.qos.logback.classic.Level.WARN);
	}

	/**
	 * Loads the configuration properties and requests information from the user.
	 * 
	 * @param configuration Hibernate configuration used for building a session
	 *                      factory.
	 */
	public void execute(Configuration configuration) {

		Properties properties = new Properties();
		try {
			properties.load(UserConsole.class.getResourceAsStream(Constant.PROPERTIES_FILE));
			configuration.configure(CONFIG);

			String driverClass = properties.getProperty("spring.datasource.driverClassName");
			configuration.setProperty("hibernate.connection.driver_class", driverClass);
			configuration.setProperty("hibernate.dialect",
					properties.getProperty("spring.jpa.properties.hibernate.dialect"));
			configuration.setProperty("hibernate.connection.url", properties.getProperty("spring.datasource.url"));
			configuration.setProperty("hibernate.connection.username",
					properties.getProperty("spring.datasource.username"));
			configuration.setProperty("hibernate.connection.password",
					properties.getProperty("spring.datasource.password"));
			configuration.setProperty("hibernate.hbm2ddl.auto",
					properties.getProperty("spring.jpa.hibernate.ddl-auto"));

			UserInformation userInformation = credentialsRequester.requestUserInformation();

			storeUser(userInformation, configuration, logger);

		} catch (IOException e) {

			logger.log(Level.SEVERE, e.getMessage());

		}

	}

	/**
	 * Creates a {@link CredentialsRequester} to request credentials from a user
	 * and stores received data using Hibernate.
	 * 
	 * @param args The arguments are not used.
	 * 
	 */
	public static void main(String[] args) {

		PrintWriter pw = new PrintWriter(System.out, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Logger logger = Logger.getLogger(UserConsole.class.getName());
		CredentialsRequester credentialsRequester = new CredentialsRequester(pw, br, new BCryptPasswordEncoder(),
				new SecureRandom());

		UserConsole uc = new UserConsole(credentialsRequester, logger);
		System.out.println("Starting");

		uc.hideInfoLogs();
		uc.execute(new Configuration());
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
