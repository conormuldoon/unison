package eu.acclimatize.unison.user;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.OwnedItem;

/**
 * 
 * An entity class user to store user credential information in the data base.
 *
 */
@Entity
public class UserInformation implements OwnedItem, Serializable {

	private static final long serialVersionUID = -6566767228133005900L;

	@Id
	@JsonProperty
	private String userName;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String encodedPassword;

	/**
	 * Creates an instance of UserInformation.
	 * 
	 * @param userName        The name of the user.
	 * @param encodedPassword The encoded password of the user.
	 */
	public UserInformation(String userName, String encodedPassword) {
		this.userName = userName;
		this.encodedPassword = encodedPassword;
	}

	/**
	 * A zero argument constructor for JPA.
	 */
	public UserInformation() {

	}

	public Optional<UserInformation> findCurrent(UserRepository userRepository) {
		return userRepository.findById(userName);
	}

	@Override
	public boolean hasOwner(String ownerName) {
		return userName.equals(ownerName);
	}

	/**
	 * Creates a Spring Security user, which is required for authentication.
	 * 
	 * @return The user created.
	 */
	public UserDetails buildUser() {

		return User.withUsername(userName).password(encodedPassword).authorities(Constant.ROLL_USER).build();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encodedPassword == null) ? 0 : encodedPassword.hashCode());
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
		if (encodedPassword == null) {
			if (other.encodedPassword != null)
				return false;
		} else if (!encodedPassword.equals(other.encodedPassword))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
