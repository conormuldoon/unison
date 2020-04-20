package eu.acclimatize.unison;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import eu.acclimatize.unison.user.AddUserController;
import eu.acclimatize.unison.user.UpdatePasswordController;
import eu.acclimatize.unison.user.UserInformation;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * Tests the controllers for managing user credentials.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTests {

	private static final String USERNAME = "conor";
	private static final String PASSWORD = "pwd";
	private static final String NEW_USERNAME = "bob";

	@Autowired
	private AddUserController addUserController;

	@Autowired
	private UpdatePasswordController updatePasswordController;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Adds an initial user.
	 */
	@Before
	public void addInitialUser() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(PASSWORD);
		userRepository.save(new UserInformation(USERNAME, encodedPassword));

	}

	/**
	 * Removes user data from the database.
	 */
	@After
	public void clearData() {
		userRepository.deleteAll();
	}

	/**
	 * Tests the controller for adding new users.
	 */
	@Test
	public void addUser() {
		addUserController.addUser(USERNAME, PASSWORD, NEW_USERNAME, "bobspassword");

		Assert.assertEquals(2, userRepository.count());
	}

	/**
	 * Tests that a pre-existing user will not be added.
	 */
	@Test
	public void existingUser() {

		userRepository.save(new UserInformation(NEW_USERNAME, "bobsEncodedPassword"));
		int res = addUserController.addUser(USERNAME, PASSWORD, NEW_USERNAME, "");
		Assert.assertEquals(ResponseConstant.FAIL, res);
	}

	/**
	 * Tests the controller for updating passwords.
	 */
	@Test
	public void updatePassword() {
		Assert.assertEquals(ResponseConstant.SUCCESS,
				updatePasswordController.updatePassword(USERNAME, PASSWORD, "pwd2"));
		Assert.assertEquals(ResponseConstant.INCORRECT_CREDENTIALS,
				updatePasswordController.updatePassword(USERNAME, PASSWORD, "pwd2"));

	}

}
