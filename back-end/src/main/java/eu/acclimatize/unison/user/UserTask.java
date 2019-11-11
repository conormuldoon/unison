package eu.acclimatize.unison.user;

/**
 * 
 * The interface is implemented by classes that wrap tasks that require user credentials to be performed.
 *
 */
public interface UserTask {

	/**
	 * Invokes the task.
	 * 
	 * @param user The stored user information from the database.
	 * @return The {@link eu.acclimatize.unison.ResponseConstant} value for the result of executing the task.
	 */
	public int execute(UserInformation user);
}
