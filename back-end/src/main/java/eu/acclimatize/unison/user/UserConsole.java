package eu.acclimatize.unison.user;

import java.io.Console;
import java.io.IOException;
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
 * A class that uses the console to request user credentials (user name and
 * password) information and stores it in the database. Credentials are
 * requested if none are present when Unison begins operating. Additionally, the
 * class can be invoked from the command line to add additional users or update
 * users' passwords.
 * 
 *
 */
public class UserConsole {

	public static final String CONFIG = "userinformation.cfg.xml";

	private static final int PLEN = 10;

	private Console console;
	private BCryptPasswordEncoder encoder;
	private SecureRandom random;

	/**
	 * Creates and instance of UserConsole.
	 * 
	 * @param console Used to request and read user data.
	 * @param encoder Used to encrypt the password.
	 */
	public UserConsole(Console console, BCryptPasswordEncoder encoder, SecureRandom random) {

		this.console = console;
		this.encoder = encoder;
		this.random = random;

	}

	private void hideInfoLogs() {
		console.printf("Starting\n");

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

			UserInformation userInformation = requestUserInformation();

			storeUser(userInformation, cfg, logger);

		} catch (IOException e) {

			logger.log(Level.WARNING, e.getMessage());

		}

	}

	/**
	 * Asks the user to enter a user name whether they would like to generate a
	 * password or enter one.
	 * 
	 * @return Contains the information entered by the user (the password is
	 *         encoded).
	 */
	public UserInformation requestUserInformation() {
		console.printf("Enter user name: ");

		String userName = console.readLine();
		console.printf("Generate password (y/N)? ");

		String gp = console.readLine().toLowerCase();
		String encoded = null;

		if (gp.startsWith("y")) {

			String password = randomPassword();
			console.printf("Generated password: %s\n", password);

			encoded = encoder.encode(password);
		} else {

			char[] passwd = System.console().readPassword("%s", "Enter password: ");
			encoded = encoder.encode(String.valueOf(passwd));
			java.util.Arrays.fill(passwd, ' '); // See Security note for Console class:
												// https://docs.oracle.com/javase/7/docs/api/java/io/Console.html

		}

		UserInformation userInformation = new UserInformation(userName, encoded);
		return userInformation;

	}

	/**
	 * Generates a random password.
	 * 
	 * @return The password generated.
	 */
	public String randomPassword() {

		StringBuilder pwd = new StringBuilder();
		for (int i = 0; i < PLEN; i++) {
			char c = (char) (random.nextInt(75) + 48);
			pwd.append(c);
		}
		return pwd.toString();

	}

	/**
	 * Creates and executes a UserConsole using the system console and a bcrypt
	 * encoder.
	 * 
	 * @param args The arguments are not used.
	 * 
	 */
	public static void main(String[] args) {

		UserConsole uc = new UserConsole(System.console(), new BCryptPasswordEncoder(), new SecureRandom());
		uc.execute(Logger.getLogger(UserConsole.class.getName()));

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
				logger.log(Level.WARNING, pe.getMessage());
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
