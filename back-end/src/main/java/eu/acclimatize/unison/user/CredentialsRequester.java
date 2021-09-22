package eu.acclimatize.unison.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Arrays;

import org.springframework.security.crypto.password.PasswordEncoder;

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
	private PasswordEncoder passwordEncoder;
	private SecureRandom random;

	/**
	 * Creates and instance of CredentialsRequester.
	 * 
	 * @param writer          Used to display information to the user.
	 * @param reader          Used to request data from the user.
	 * @param passwordEncoder Used to encrypt passwords.
	 * @param random          Used in generating random passwords.
	 */
	public CredentialsRequester(PrintWriter writer, BufferedReader reader, PasswordEncoder passwordEncoder,
			SecureRandom random) {

		this.writer = writer;
		this.reader = reader;
		this.passwordEncoder = passwordEncoder;
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
		writer.print("Enter user name: ");
		writer.flush();

		String userName = reader.readLine();
		
		writer.print("Generate password (y/N)? ");
		writer.flush();

		String gp = reader.readLine().toLowerCase();
		String encodedPassword = null;

		if (gp.startsWith("y")) {

			String password = randomPassword();
			writer.printf("Generated password: %s%n", password);
			encodedPassword = passwordEncoder.encode(password);

		} else {

			boolean match;
			do {

				char[] passwd = System.console().readPassword("%s", "Enter password: ");
				char[] confirmPWD = System.console().readPassword("%s", "Confirm password: ");
				match = Arrays.equals(passwd, confirmPWD);
				Arrays.fill(confirmPWD, ' ');
				if (match) {
					encodedPassword = passwordEncoder.encode(new String(passwd));
				}
				Arrays.fill(passwd, ' ');
				if (match) {
					writer.printf("Password confirmed.%n");
				} else {
					writer.printf("The passwords don't match.%n");
				}
				

			} while (!match);
		}

		return new UserInformation(userName, encodedPassword);

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
