package bp.eventSets;

import java.io.Serializable;

/**
 * @author Bertrand Russell
 * 
 *         A set that contains everything.
 */
public class AllEvents implements EventSetInterface, Serializable {
	/**
	 * @see bp.eventSets.EventSetInterface#contains(Object)
	 */
	public boolean contains(Object o) {
		return true;
	}

	public String toString() {
		return ("All");
	}

//	@Override
//	public String jsIdentifier() {
//		return "All";
//	}
}
