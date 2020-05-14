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
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@Test
	public void addUser() {

		TestRestTemplate templateWBA = template.withBasicAuth(TestConstant.USERNAME, TestConstant.PASSWORD);
		templateWBA.put(MappingConstant.USER+"/"+TestConstant.USERNAME, TestUtility.createUserInformation(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD));

		Assert.assertEquals(2, userRepository.count());
	}

	/**
	 * 
	 * Using a stub for serializing the user information as the TestRestTemplate
	 * performs serialization prior to performing a put and the encoded password in
	 * the UserInformation has only write access.
	 *
	 */
	private class SerializationStub {
		@JsonProperty
		private String userName;

		@JsonProperty /**
						 * In the user UserInformation class, the JSON property for the encoded password
						 * has write access, but not read access. It has read access only for
						 * serialization purposes.
						 * 
						 **/
		private String encodedPassword;

		private SerializationStub(String userName, String encodedPassword) {
			this.userName = userName;
			this.encodedPassword = encodedPassword;
		}

	}

	private void testUpdate(TestRestTemplate templateWBA, boolean expected, String userName, String password) {

		String encodedPassword = TestUtility.encode(password);
		SerializationStub serializationStub = new SerializationStub(userName, encodedPassword);
		templateWBA.put(MappingConstant.USER + "/" + userName, serializationStub);
		UserInformation savedUserInformation = userRepository.findById(userName).get();

		UserInformation userInformation = new UserInformation(userName, encodedPassword);
		Assert.assertEquals(expected, userInformation.equals(savedUserInformation));
	}

	private void testUpdate(TestRestTemplate templateWBA, boolean expected, String password) {
		testUpdate(templateWBA, expected, TestConstant.USERNAME, password);
	}

	/**
	 * Tests the controller for updating passwords.
	 */
	@Test
	public void updatePasswordUser() {

		// Tests that the current password works and updates the current password to be
		// the second password.
		TestRestTemplate templateWBA = template.withBasicAuth(TestConstant.USERNAME, TestConstant.PASSWORD);

		testUpdate(templateWBA, true, SECOND_PASSWORD);

		// Tests that original password does not work.
		testUpdate(templateWBA, false, TestConstant.PASSWORD);

		// Tests that the second password works and updates the current password to be
		templateWBA = template.withBasicAuth(TestConstant.USERNAME, SECOND_PASSWORD);
		testUpdate(templateWBA, true, TestConstant.PASSWORD);

	}

	/**
	 * Tests that the authenticated user cannot update the passwords of other users.
	 */
	@Test
	public void updatePasswordOther() {

		TestRestTemplate templateWBA = template.withBasicAuth(TestConstant.USERNAME, TestConstant.PASSWORD);
		TestUtility.addUserInformation(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD, userRepository);
		testUpdate(templateWBA, false, TestConstant.OTHER_USERNAME, TestConstant.PASSWORD);
	}

}
