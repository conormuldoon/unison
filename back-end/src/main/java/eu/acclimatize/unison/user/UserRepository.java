package eu.acclimatize.unison.user;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * A repository that stores user credentials information.
 *
 */
//@RepositoryRestResource(exported=false)
public interface UserRepository extends CrudRepository<UserInformation, String> {

}
