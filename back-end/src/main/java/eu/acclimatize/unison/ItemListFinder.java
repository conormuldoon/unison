package eu.acclimatize.unison;

import static eu.acclimatize.unison.Constant.FROM_DATE;
import static eu.acclimatize.unison.Constant.LOCATION;
import static eu.acclimatize.unison.Constant.TO_DATE;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ItemListFinder {

	private String query;
	EntityManager entityManager;

	public ItemListFinder(EntityManager entityManager, String query) {
		this.entityManager = entityManager;
		this.query = query;

	}

	public List<HarmonieItem> find(String location, Date fromDate, Date toDate) {

		TypedQuery<HarmonieItem> typedQuery = entityManager.createQuery(query, HarmonieItem.class);
		typedQuery.setParameter(LOCATION, location);
		typedQuery.setParameter(FROM_DATE, fromDate);
		typedQuery.setParameter(TO_DATE, toDate);
		return typedQuery.getResultList();
	}

}
