package bp.eventSets;

import bp.JSIdentifiable;

/**
 * An helper class for implementing sets with minimal method implementation
 * (e.g. only contains). This class implements all the functions (that the user
 * does not to implement) by throwing an exception.
 */
public interface EventSetInterface extends JSIdentifiable {
	/**
	 * A function that implements the set membership function.
	 * 
	 * @param o
	 *            A candidate object to be tested for matching the criteria of
	 *            the set.
	 * @return true if the object matches the criteria of the set.
	 */
	public boolean contains(Object o);

}
