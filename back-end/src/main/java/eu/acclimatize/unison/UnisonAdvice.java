package eu.acclimatize.unison;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import eu.acclimatize.unison.location.DeserializationException;
import eu.acclimatize.unison.location.LocationExistsException;
import eu.acclimatize.unison.location.LocationNotFoundException;
import eu.acclimatize.unison.location.LocationRequestException;
import eu.acclimatize.unison.user.UserExistsException;

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
	public String locationDeserializationHandler(LocationRequestException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(LocationRequestException.class)
	@ResponseStatus(HttpStatus.OK)
	public String locationRequestHandler(LocationRequestException exception) {
		return handle(exception);
	}

	@ResponseBody
	@ExceptionHandler(LocationExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String locationExistsHandler(LocationExistsException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(UserExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String userExistsHandler(UserExistsException exception) {
		return exception.getMessage();
	}

}
