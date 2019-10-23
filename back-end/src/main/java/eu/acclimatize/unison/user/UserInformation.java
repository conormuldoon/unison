package eu.acclimatize.unison.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * An entity class user to store user crediatial information in the data base.
 *
 */
@Entity
public class UserInformation implements Serializable{

	private static final long serialVersionUID = -6566767228133005900L;

	@Id
	private String userName;

	private String passwordBCrypt;

	/**
	 * Creates an instance of UserInformation.
	 * 
	 * @param userName The name of the user.
	 * @param passwordBCrypt An encoded password.
	 */
	public UserInformation(String userName, String passwordBCrypt) {
		this.userName = userName;
		this.passwordBCrypt = passwordBCrypt;
	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public UserInformation() {

	}

	/**
	 * Checks whether the given password matches the stored encoded password.
	 * 
	 * @param password The password to check.
	 * @param passwordEncoder The encoder to perform the check.
	 * @return True if the password matches and false otherwise.
	 */
	public boolean passwordMatches(String password, BCryptPasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(password, passwordBCrypt);
	}

}
