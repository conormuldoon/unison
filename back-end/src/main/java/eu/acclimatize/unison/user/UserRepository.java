package eu.acclimatize.unison.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInformation, String> {

}
