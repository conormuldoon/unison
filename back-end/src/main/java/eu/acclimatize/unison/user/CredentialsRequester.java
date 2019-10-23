package eu.acclimatize.unison.user;

import java.io.Console;
import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * A class that uses the console to request user credentials (user name and
 * password) information. Credentials are requested if none are present when
 * Unison begins operating or when {@link UserConsole} is run from the
 * terminal.
 * 
 */
public class CredentialsRequester {

	private static final int PLEN = 10;

	private Console console;
	private BCryptPasswordEncoder encoder;
	private SecureRandom random;

	/**
	 * Creates and instance of UserConsole.
	 * 
	 * @param console Used to request and read user data.
	 * @param encoder Used to encrypt the password.
	 * @param random  Used in generating random passwords.
	 */
	public CredentialsRequester(Console console, BCryptPasswordEncoder encoder, SecureRandom random) {

		this.console = console;
		this.encoder = encoder;
		this.random = random;

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
