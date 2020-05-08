package eu.acclimatize.unison;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import eu.acclimatize.unison.user.AddUserController;
import eu.acclimatize.unison.user.UserInformation;
import eu.acclimatize.unison.user.UserRepository;

/**
 * 
 * Tests the controllers for managing user credentials.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTests {

	private static final String SECOND_PASSWORD = "pwd2";

	@Autowired
	private AddUserController userController;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestRestTemplate template;

	/**
	 * Adds an initial user.
	 */
	@Before
	public void addInitialUser() {

		TestUtility.saveUserData(userRepository);

	}

	/**
	 * Removes user data from the database.
	 */
	@After
	public void clearData() {

		TestUtility.deleteUserData(userRepository);

	}

	/**
	 * Tests the controller for adding new users.
	 */
	@WithMockUser(username = TestConstant.USERNAME)
	@Test
	public void addUser() {

		userController.addUser(
				TestUtility.createUserInformation(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD));

		Assert.assertEquals(2, userRepository.count());
	}

	/**
	 * Tests that a pre-existing user will not be added.
	 */
	@WithMockUser(username = TestConstant.USERNAME)
	@Test
	public void existingUser() {

		TestUtility.addUserInformation(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD, userRepository);

		int result = userController
				.addUser(new UserInformation(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD));
		Assert.assertEquals(ResponseConstant.FAILURE, result);
	}

	private void testStatus(TestRestTemplate templateWBA, HttpStatus expectedStatus, String password) {
		ResponseEntity<Void> result = templateWBA.postForEntity(Constant.UPDATE_PASSWORD_MAPPING, password, Void.class);
		Assert.assertEquals(expectedStatus, result.getStatusCode());
	}

	/**
	 * Tests the controller for updating passwords.
	 */
	@Test
	public void updatePassword() {

		// Tests that the current password works and updates the current password to be
		// the second password.
		TestRestTemplate templateWBA = template.withBasicAuth(TestConstant.USERNAME, TestConstant.PASSWORD);
		testStatus(templateWBA, HttpStatus.OK, SECOND_PASSWORD);

		// Tests that original password does not work.
		testStatus(templateWBA, HttpStatus.UNAUTHORIZED, null);

		// Tests that the second password works and updates the current password to be
		templateWBA = template.withBasicAuth(TestConstant.USERNAME, SECOND_PASSWORD);
		testStatus(templateWBA, HttpStatus.OK, TestConstant.PASSWORD);

	}

}
