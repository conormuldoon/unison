package eu.acclimatize.unison.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Arrays;

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
	private SecureRandom random;

	/**
	 * Creates and instance of CredentialsRequester.
	 * 
	 * @param writer Used to display information to the user.
	 * @param reader Used to request data from the user.
	 * @param random Used in generating random passwords.
	 */
	public CredentialsRequester(PrintWriter writer, BufferedReader reader, SecureRandom random) {

		this.writer = writer;
		this.reader = reader;
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
		String password = null;

		if (gp.startsWith("y")) {

			password = randomPassword();
			writer.printf("Generated password: %s%n", password);

		} else {

			boolean different;
			do {

				char[] passwd = System.console().readPassword("%s", "Enter password: ");
				char[] confirmPWD = System.console().readPassword("%s", "Confirm password: ");
				if (Arrays.equals(passwd, confirmPWD)) {
					different = false;
					writer.printf("Password confirmed.\n");
					password = new String(passwd);
				} else {
					different = true;
					writer.printf("The passwords don't match.\n");
				}

				Arrays.fill(passwd, ' ');
				Arrays.fill(confirmPWD, ' ');

			} while (different);
		}

		return new UserInformation(userName, password);

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
