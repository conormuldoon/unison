package eu.acclimatize.unison.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.acclimatize.unison.MappingConstant;
import eu.acclimatize.unison.TestConstant;
import eu.acclimatize.unison.TestUtility;

/**
 * 
 * Tests the controllers for managing user credentials.
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTests {

	private static final String SECOND_PASSWORD = "pwd2";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Adds an initial user.
	 */
	@BeforeEach
	void addInitialUser() {

		TestUtility.saveUserData(userRepository);

	}

	/**
	 * Removes user data from the database.
	 */
	@AfterEach
	void clearData() {

		TestUtility.deleteUserData(userRepository);

	}

	private String jsonUInfo(String userName, String password) throws JsonProcessingException {

		UserInfoDTO userInfoDTO = new UserInfoDTO(userName, TestUtility.encode(password));
		return new ObjectMapper().writeValueAsString(userInfoDTO);
	}

	/**
	 * Tests the controller for adding new users.
	 * 
	 * @throws Exception
	 */
	@Test
	void addUser() throws Exception {

		String json = jsonUInfo(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD);
		mockMvc.perform(put(MappingConstant.USER).contentType(MediaType.APPLICATION_JSON).content(json).with(csrf())
				.with(user(TestConstant.USERNAME).password(TestConstant.PASSWORD)));

		Assertions.assertEquals(2, userRepository.count());
	}

	private void testUpdate(boolean expected, String userName, String password) throws Exception {

		String json = jsonUInfo(userName, password);

		mockMvc.perform(put(MappingConstant.USER).contentType(MediaType.APPLICATION_JSON).content(json)
				.with(httpBasic(TestConstant.USERNAME, TestConstant.PASSWORD)).with(csrf()));

		Optional<UserInformation> savedUIOpt = userRepository.findById(userName);

		Assertions.assertTrue(savedUIOpt.isPresent());
		
	}

	/**
	 * Tests the controller for updating passwords.
	 * 
	 * @throws Exception
	 */
	@Test
	void updatePasswordUser() throws Exception {

		testUpdate(true, TestConstant.USERNAME, SECOND_PASSWORD);
		testUpdate(false, TestConstant.USERNAME, TestConstant.PASSWORD);
		testUpdate(true, TestConstant.USERNAME, SECOND_PASSWORD);

	}

	/**
	 * Tests that the authenticated user cannot update the passwords of other users.
	 * 
	 * @throws Exception
	 */
	@Test
	void updatePasswordOther() throws Exception {

		TestUtility.addUserInformation(TestConstant.OTHER_USERNAME, TestConstant.OTHER_USER_PASSWORD, userRepository);
		testUpdate(false, TestConstant.OTHER_USERNAME, TestConstant.PASSWORD);
	}

}
