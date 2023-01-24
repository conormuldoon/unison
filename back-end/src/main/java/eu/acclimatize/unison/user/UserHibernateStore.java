package eu.acclimatize.unison.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;

import eu.acclimatize.unison.Constant;

/**
 * 
 * A class that obtains and stores user credentials (user name and password) in
 * the database without launching the Spring application. Hibernate is
 * configured using the application.properties file to ensure the tool is
 * configured the same as Unison Spring Boot application.
 * 
 *
 */
public class UserHibernateStore {

	private static final String CONFIG = "userinformation.cfg.xml";
	
	private static final String INCOMPLETE_INIT = "./harmonie.iinit";

	private CredentialsRequester credentialsRequester;

	private Logger logger;

	/**
	 * Creates an instance of UserHibernteStore.
	 * 
	 * @param credentialsRequester Used to request and read user names and
	 *                             passwords.
	 * @param logger               Used for logging errors.
	 * 
	 */
	public UserHibernateStore(CredentialsRequester credentialsRequester, Logger logger) {

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

	private void configAuto(Configuration configuration, Properties properties) {
		configuration.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("spring.jpa.hibernate.ddl-auto"));
	}

	/**
	 * 
	 * Logs a message that if the incomplete initialisation file could not be created.
	 * 
	 * @param file The initialisation file.
	 * @throws IOException Thrown if an I/O error occurs.
	 */
	public void createIncompleteInit(File file) throws IOException {
		if (!file.createNewFile()) {
			logger.log(Level.SEVERE, "Failed to create incomplete initialisation file: " + INCOMPLETE_INIT);
		}
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

			boolean h2 = driverClass.equals("org.h2.Driver");
			if (h2) {
				String dbFN = "./harmonie.mv.db";
				File dbFile = new File(dbFN);
				File incompleteInit = new File(INCOMPLETE_INIT);

				if (incompleteInit.exists()) {
					
					Path p=Paths.get("./harmonie.mv.db");
					Files.delete(p);

				} else {
					createIncompleteInit(incompleteInit);
				}

				if (dbFile.exists()) {

					configuration.setProperty("hibernate.hbm2ddl.auto", "none");

				} else {
					configAuto(configuration, properties);
				}
			} else {
				configAuto(configuration, properties);

			}

			UserInformation userInformation = credentialsRequester.requestUserInformation();
			if (h2) {
				storeUserII(userInformation, configuration, logger);
			} else {
				storeUser(userInformation, configuration, logger);
			}

		} catch (IOException e) {

			logger.log(Level.SEVERE, e.getMessage());

		}

	}

	private void storeUserII(UserInformation user, Configuration configuration, Logger logger) throws IOException {

		if (storeUser(user, configuration, logger)) {
			
			Path p=Paths.get(INCOMPLETE_INIT);
			Files.delete(p);
			
		}
	}

	private boolean storeUser(UserInformation user, Configuration configuration, Logger logger) {
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
				return true;
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
		return false;

	}

}
