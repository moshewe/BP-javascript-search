package bp.eventSets;

import bp.BEvent;
import bp.exceptions.BPJRequestableSetException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A filter that doesn't match any object.
 */
public class EmptyEventSet implements EventSetInterface, RequestableInterface, Serializable {
	/**
	 * @see bp.eventSets.EventSetInterface#contains(Object)
	 */
	public boolean contains(Object o) {
		return false;
	}

	public Iterator<RequestableInterface> iterator() {
		return new EmptyEventIterator();
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}

	public RequestableInterface get(int index) {
		throw new ArrayIndexOutOfBoundsException();
	}

	public int size() {
		return (0);
	}

	public void addEventsToList(ArrayList<BEvent> list) {
		// Just return
	}

	public BEvent getEvent() throws BPJRequestableSetException {
		throw new BPJRequestableSetException();
	}

	public ArrayList<BEvent> getEventList() {
		return (new ArrayList<BEvent>());
	}
	public boolean isEvent() {
		return false;
	}

//	@Override
//	public String jsIdentifier() {
//		return "emptyEventSet";
//	}
}

/**
 * An iterator over an empty set of events.
 */
class EmptyEventIterator implements Iterator<RequestableInterface> {
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public BEvent next() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
