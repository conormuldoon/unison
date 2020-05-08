package eu.acclimatize.unison.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import eu.acclimatize.unison.location.LocationService;

/**
 * 
 * An entity class user to store user credential information in the data base.
 *
 */
@Entity
public class UserInformation implements Serializable {

	private static final String ROLL = "USER";
	private static final long serialVersionUID = -6566767228133005900L;

	@Id
	private String userName;

	private String encodedPassword;

	/**
	 * Creates an instance of UserInformation.
	 * 
	 * @param userName The name of the user.
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

	/**
	 * Checks if the user information exists in the user repository.
	 * 
	 * @param userRepository The repository to check.
	 * @return True if the user information is stored in the repository or false
	 *         otherwise.
	 */
	public boolean existIn(UserRepository userRepository) {
		return userRepository.existsById(userName);
	}

	/**
	 * Creates a Spring Security user, which is required for authentication.
	 * 
	 * @return The user created.
	 */
	public UserDetails buildUser() {

		return User.withUsername(userName).password(encodedPassword).authorities(ROLL).build();
	}

	/**
	 * Deletes the location and associated harvested data.
	 * 
	 * @param locationName    The name of the location to delete.
	 * @param locationService The location service used to delete the data.
	 */
	public void deleteLocation(String locationName, LocationService locationService) {
		locationService.delete(locationName, userName);
	}

}
