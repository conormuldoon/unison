package eu.acclimatize.unison;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import eu.acclimatize.unison.harvester.DocumentNotFoundException;
import eu.acclimatize.unison.harvester.HarvestParseException;
import eu.acclimatize.unison.location.DeserializationException;
import eu.acclimatize.unison.location.LocationNotFoundException;
import eu.acclimatize.unison.location.LocationUpdateException;
import eu.acclimatize.unison.user.AnonymousUserException;
import eu.acclimatize.unison.user.UserUpdateException;

/**
 * 
 * A class for the Unison exception handlers.
 *
 */
@ControllerAdvice
public class UnisonAdvice {

	private Logger logger;

	public UnisonAdvice(Logger logger) {
		this.logger = logger;
	}

	private String handle(Exception e) {
		String message = e.getMessage();
		logger.log(Level.SEVERE, e.getMessage());
		return message;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(LocationNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String locationNotFoundHandler(LocationNotFoundException exception) {
		return handle(exception);
	}

	@ResponseBody
	@ExceptionHandler(DeserializationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String locationDeserializationHandler(HarvestParseException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(LocationUpdateException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String locationUpdateHandler(LocationUpdateException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(UserUpdateException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String userUpdateHandler(UserUpdateException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(HarvestParseException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public String locationRequestHandler(HarvestParseException exception) {
		return handle(exception);
	}

	@ResponseBody
	@ExceptionHandler(DocumentNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public String documentNotFoundHandler(HarvestParseException exception) {
		return exception.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(AnonymousUserException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String anonymousUserHandler(HarvestParseException exception) {
		return exception.getMessage();
	}

}
