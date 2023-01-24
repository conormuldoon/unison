package eu.acclimatize.unison.user;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.acclimatize.unison.BaseURIBuilder;
import eu.acclimatize.unison.Constant;
import eu.acclimatize.unison.MappingConstant;

/**
 * A controller for adding or updating the authenticated user.
 *
 */
@RestController
public class UpsertUserController {

	private UserRepository userRepository;
	private UserService userService;
	private BaseURIBuilder builder;

	/**
	 * Creates and instance of UpsertUserController.
	 * 
	 * @param userRepository The repository that stores user data.
	 * @param userService    The service used to update the user.
	 * @param builder        Used to create a base URI for the user name link.
	 * 
	 */
	public UpsertUserController(UserRepository userRepository, UserService userService, BaseURIBuilder builder) {

		this.userRepository = userRepository;
		this.userService = userService;
		this.builder = builder;

	}

	/**
	 * Updates the authenticated user or stores new user information if there are no
	 * users with the same user name. The user can only update their own
	 * information.
	 * 
	 * 
	 * @param userInfoDTO The transfer object for new user information.
	 * @param response    The response that a location header is added to if a new
	 *                    user was stored rather than an existing user updated.
	 * @param request     Used to create a base URI for HAL.
	 */
	// The roles allowed annotation is not required here as MappingConstant.USER is
	// configured to require authorization in the UnisonSecurityConfig
	// configuration.
	@PutMapping(MappingConstant.USER)
	public void upsertUser(@RequestBody UserInfoDTO userInfoDTO, HttpServletResponse response,
			HttpServletRequest request) {

		UserInformation userInformation = userInfoDTO.createEntity();

		Optional<UserInformation> optCurrent = userInformation.findCurrent(userRepository);
		if (optCurrent.isPresent()) {

			try {
				userService.replace(optCurrent.get(), userInformation);
			} catch (AccessDeniedException e) {

				throw new UserUpdateException(
						"Users may only update their own information. The name of the user in the response body"
								+ " must match that of the authenticated user.");
			}
		} else {
			userRepository.save(userInformation);
			response.setStatus(Constant.CREATED);
			String baseURI = builder.build(request.getScheme(), request.getServerName(), request.getServerPort(),
					request.getContextPath());
			userInformation.addHeader(response, baseURI);
		}

	}
}
