package eu.acclimatize.unison.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eu.acclimatize.unison.location.LocationDetails;

/**
 * 
 * An entity class user to store user crediatial information in the data base.
 *
 */
@Entity
public class UserInformation implements Serializable {

	private static final long serialVersionUID = -6566767228133005900L;

	@Id
	private String userName;

	private String passwordBCrypt;

	/**
	 * Creates an instance of UserInformation.
	 * 
	 * @param userName       The name of the user.
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
	 * @param password        The password to check.
	 * @param passwordEncoder The encoder to perform the check.
	 * @return True if the password matches and false otherwise.
	 */
	public boolean passwordMatches(String password, BCryptPasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(password, passwordBCrypt);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passwordBCrypt == null) ? 0 : passwordBCrypt.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInformation other = (UserInformation) obj;
		if (passwordBCrypt == null) {
			if (other.passwordBCrypt != null)
				return false;
		} else if (!passwordBCrypt.equals(other.passwordBCrypt))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
