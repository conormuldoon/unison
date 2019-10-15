package eu.acclimatize.unison;

import static eu.acclimatize.unison.Constant.FROM_DATE;
import static eu.acclimatize.unison.Constant.LOCATION;
import static eu.acclimatize.unison.Constant.TO_DATE;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * 
 * A class that finds a list of items based on a JPQL query.
 *
 */
public class ItemListFinder {

	private String query;
	private EntityManager entityManager;

	/**
	 * Creates an instance of ItemListFinder.
	 * 
	 * @param entityManager The JPA entity manager.
	 * @param query The query to be executed.
	 */
	public ItemListFinder(EntityManager entityManager, String query) {
		this.entityManager = entityManager;
		this.query = query;

	}

	/**
	 * Returns a list of items based on the query parameters.
	 * 
	 * @param location The location of the weather or precipitation value.
	 * @param fromDate The start date (inclusive) for the query.
	 * @param toDate The end date (inclusive) for the query.
	 * @return The list of items that match the query parameters.
	 */
	public List<HarmonieItem> find(String location, Date fromDate, Date toDate) {

		TypedQuery<HarmonieItem> typedQuery = entityManager.createQuery(query, HarmonieItem.class);
		typedQuery.setParameter(LOCATION, location);
		typedQuery.setParameter(FROM_DATE, fromDate);
		typedQuery.setParameter(TO_DATE, toDate);
		return typedQuery.getResultList();
	}

}
