package eu.acclimatize.unison.user;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class UserInformation {

	@Id
	private String userName;

	private String passwordBCrypt;

	public UserInformation(String userName, String passwordBCrypt) {
		this.userName = userName;
		this.passwordBCrypt = passwordBCrypt;
	}

	public UserInformation() {

	}

	public boolean passwordMatches(String password, BCryptPasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(password, passwordBCrypt);
	}

}
