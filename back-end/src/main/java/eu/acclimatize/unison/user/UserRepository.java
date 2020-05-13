package eu.acclimatize.unison.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * A repository that stores user credentials information.
 *
 */
public interface UserRepository extends JpaRepository<UserInformation, String> {

}
