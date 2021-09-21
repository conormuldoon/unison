package eu.acclimatize.unison.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class UserInfoDTO {

	@JsonProperty
	private String userName;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String encodedPassword;

	public UserInformation createEntity() {
		return new UserInformation(userName, encodedPassword);
	}
}
