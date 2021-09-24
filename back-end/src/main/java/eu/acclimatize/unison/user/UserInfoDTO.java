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
	 * Creates a JPA entity for the user information.
	 * 
	 * @return The created entity.
	 */
	public UserInformation createEntity() {
		return new UserInformation(userName, encodedPassword);
	}
}
