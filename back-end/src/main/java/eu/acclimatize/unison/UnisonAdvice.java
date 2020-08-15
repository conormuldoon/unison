package eu.acclimatize.unison;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import eu.acclimatize.unison.location.DeserializationException;
import eu.acclimatize.unison.location.DeserializationUserException;
import eu.acclimatize.unison.location.LocationNotFoundException;
import eu.acclimatize.unison.location.LocationUpdateException;
import eu.acclimatize.unison.location.harvester.DocumentNotFoundException;
import eu.acclimatize.unison.location.harvester.HarvestParseException;
import eu.acclimatize.unison.user.UserUpdateException;

/**
 * 
 * A class for the Unison exception handlers.
 *
 */
@ControllerAdvice
public class UnisonAdvice{

	private Logger logger;

	/**
	 * Creates an instance of UnisonAdvice.
	 * 
	 * @param logger Used for logging exception messages.
	 */
	public UnisonAdvice(Logger logger) {
		this.logger = logger;
	}

	private String handle(Exception e) {
		String message = e.getMessage();
		logger.log(Level.SEVERE, e.getMessage());
		return message;
	}

	/**
	 * Used for handling location not found exceptions.
	 * 
	 * @param exception The exception thrown.
	 * @return The exception message.
	 */
	@ResponseBody
	@ExceptionHandler(LocationNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String locationNotFoundHandler(LocationNotFoundException exception) {
		return handle(exception);
	}

	/**
	 * Used for handling deserialization exceptions thrown for location data.
	 * 
	 * @param exception The exception thrown.
	 * @return The exception message.
	 */
	@ResponseBody
	@ExceptionHandler(DeserializationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String locationDeserializationHandler(DeserializationException exception) {
		return exception.getMessage();
	}

	/**
	 * Used for handling location update exceptions.
	 * 
	 * @param exception The exception thrown.
	 * @return The exception message.
	 */
	@ResponseBody
	@ExceptionHandler(LocationUpdateException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String locationUpdateHandler(LocationUpdateException exception) {
		return exception.getMessage();
	}

	/**
	 * Used for handling user update exceptions.
	 * 
	 * @param exception The exception thrown.
	 * @return The exception message.
	 */
	@ResponseBody
	@ExceptionHandler(UserUpdateException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String userUpdateHandler(UserUpdateException exception) {
		return exception.getMessage();
	}

	/**
	 * Used for handling exceptions thrown when parsing harvested data.
	 * 
	 * @param exception The exception thrown.
	 * @return The exception message.
	 */
	@ResponseBody
	@ExceptionHandler(HarvestParseException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public String locationRequestHandler(HarvestParseException exception) {
		return handle(exception);
	}

	/**
	 * Used for handling document not found exceptions thrown when harvesting data.
	 * 
	 * @param exception The exception thrown.
	 * @return The exception message.
	 */
	@ResponseBody
	@ExceptionHandler(DocumentNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public String documentNotFoundHandler(DocumentNotFoundException exception) {
		return exception.getMessage();
	}
	
	/**
	 * Used for handling exceptions thrown when deserializing user data.
	 * 
	 * @param exception The exception thrown.
	 * @return The exception message.
	 */
	@ResponseBody
	@ExceptionHandler(DeserializationUserException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String anonymousUserHandler(DeserializationUserException exception) {
		return exception.getMessage();
	}

}
