package eu.acclimatize.unison.location.harvester;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

/**
 * A CRUD repository for storing unknown weather variable data.
 *
 */
public interface UnknownWVRepository extends CrudRepository<UnknownWV, Date> {

}
