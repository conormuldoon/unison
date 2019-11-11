package eu.acclimatize.unison.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * A class that uses the console to request user credentials (user name and
 * password) information. Credentials are requested if none are present when
 * Unison begins operating or when {@link UserConsole} is run from the terminal.
 * 
 */
public class CredentialsRequester {

	private static final int PLEN = 10;

	private PrintWriter writer;
	private BufferedReader reader;
	private BCryptPasswordEncoder encoder;
	private SecureRandom random;

	/**
	 * Creates and instance of CredentialsRequester.
	 * 
	 * @param writer  Used to display information to the user.
	 * @param reader  Used to request data from the user.
	 * @param encoder Used to encrypt the password.
	 * @param random  Used in generating random passwords.
	 */
	public CredentialsRequester(PrintWriter writer, BufferedReader reader, BCryptPasswordEncoder encoder,
			SecureRandom random) {

		this.writer = writer;
		this.reader = reader;
		this.encoder = encoder;
		this.random = random;

	}

	/**
	 * Asks the user to enter a user name whether they would like to generate a
	 * password or enter one.
	 * 
	 * @return Contains the information entered by the user (the password is
	 *         encoded).
	 * @throws IOException Thrown if there is an I/O problem reading or writing
	 *                     data.
	 */
	public UserInformation requestUserInformation() throws IOException {
		writer.printf("Enter user name: ");

		String userName = reader.readLine();
		writer.printf("Generate password (y/N)? ");

		String gp = reader.readLine().toLowerCase();
		String encoded = null;

		if (gp.startsWith("y")) {

			String password = randomPassword();
			writer.printf("Generated password: %s%n", password);

			encoded = encoder.encode(password);
		} else {

			char[] passwd = System.console().readPassword("%s", "Enter password: ");
			encoded = encoder.encode(String.valueOf(passwd));
			java.util.Arrays.fill(passwd, ' '); // See Security note for Console class:
												// https://docs.oracle.com/javase/7/docs/api/java/io/Console.html

		}

		return new UserInformation(userName, encoded);

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
}
