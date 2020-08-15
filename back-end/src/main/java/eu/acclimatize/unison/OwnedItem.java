package eu.acclimatize.unison;

/**
 * An interface for items that have an owner.
 *
 */
public interface OwnedItem {

	/**
	 * Determines ownership for the item.
	 * 
	 * @param ownerName The name of the owner to check.
	 * @return True if the specified owner owns the item and false otherwise.
	 */
	public boolean hasOwner(String ownerName);
}
