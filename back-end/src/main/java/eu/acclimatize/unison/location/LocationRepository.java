package eu.acclimatize.unison.location;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 
 * A repository for storing location data.
 *
 */
//@RepositoryRestResource(exported = false)
public interface LocationRepository extends PagingAndSortingRepository<Location, String> {

}
