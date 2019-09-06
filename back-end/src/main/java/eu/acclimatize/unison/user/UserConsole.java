package eu.acclimatize.unison.user;

import java.io.Console;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.Constant;

public class UserConsole {

	public static final String CONFIG = "userinformation.cfg.xml";

	private static final int PLEN = 10;

	Console console;
	BCryptPasswordEncoder encoder;

	public UserConsole(Console console, BCryptPasswordEncoder encoder) {

		this.console = console;
		this.encoder = encoder;

	}

	void hideInfoLogs() {
		console.printf("Starting\n");

		System.setProperty("org.jboss.logging.provider", "jdk");
		Logger loggerHibernate = Logger.getLogger("org.hibernate");
		loggerHibernate.setLevel(Level.WARNING);

		ch.qos.logback.classic.Logger mchangeLogger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger("com.mchange.v2");
		mchangeLogger.setLevel(ch.qos.logback.classic.Level.WARN);
	}

	void execute() {
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

			storeUser(userInformation, cfg);

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public UserInformation requestUserInformation() {
		console.printf("Enter user name: ");

		String userName = console.readLine();
		console.printf("Generate password (y/N)? ");

		String gp = console.readLine().toLowerCase();
		String encoded = null;

		if (gp.startsWith("y")) {

			String password = randomPassword();
			console.printf("Generated password: %s\n", password);

			encoded = encodePassword(password);
		} else {

			char[] passwd = System.console().readPassword("%s", "Enter password: ");
			encoded = encodePassword(String.valueOf(passwd));
			java.util.Arrays.fill(passwd, ' '); // See Security note for Console class:
												// https://docs.oracle.com/javase/7/docs/api/java/io/Console.html

		}

		UserInformation userInformation = new UserInformation(userName, encoded);
		return userInformation;

	}

	// Generates a random password
	public String randomPassword() {
		Random r = new Random();
		StringBuilder pwd = new StringBuilder();
		for (int i = 0; i < PLEN; i++) {
			char c = (char) (r.nextInt(75) + 48);
			pwd.append(c);
		}
		return pwd.toString();

	}

	// Encodes password
	public String encodePassword(CharSequence password) {

		String encoded = encoder.encode(password);

		return encoded;
	}

	public static void main(String[] args) throws IOException {

		UserConsole uc = new UserConsole(System.console(), new BCryptPasswordEncoder());
		uc.execute();

	}

	public void storeUser(UserInformation user, Configuration configuration) {
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
				pe.printStackTrace();
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
