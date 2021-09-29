package eu.acclimatize.unison.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * 
 * A DTO for receiving user information. The password must be bcrypt encoded
 * prior to being transferred to the server. Jackson is used to deserialize the
 * received JSON and construct the DTO instance.
 *
 */
public class UserInfoDTO {

	@JsonProperty
	private String userName;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String encodedPassword;

	/**
	 * Creates and instance of UserInfoDTO. Specifying a constructor is not required
	 * by Jackson, but enables a POJO to be created for testing.
	 * 
	 * @param userName        The name of the user.
	 * @param encodedPassword The bcrypt encoded password.
	 */
	public UserInfoDTO(String userName, String encodedPassword) {
		this.userName = userName;
		this.encodedPassword = encodedPassword;
	}

	/**
	 * Creates a JPA entity for the user information.
	 * 
	 * @return The created entity.
	 */
	public UserInformation createEntity() {
		return new UserInformation(userName, encodedPassword);
	}
}
