package bp;

import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import bp.exceptions.BPJRequestableSetException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A base class for events
 */
@SuppressWarnings("serial")
public abstract class BEvent implements EventSetInterface, RequestableInterface, Comparable<BEvent>{

	static int numerator = 0;
	int id = numerator++;
	
	private String name = this.getClass().getSimpleName();

	@Override
	public boolean contains(Object o) {
		return this.equals(o);
	}

	public Iterator<RequestableInterface> iterator() {
		return new SingleEventIterator(this);
	}

	public BEvent() {
	}

	public BEvent(String name) {
		this.name = name;
	}

	public String toString() {
		return name + "(ID=" + id+  ")";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BEvent get(int index) {
		if (index == 0)
			return (this);
		throw (new ArrayIndexOutOfBoundsException());
	}

	public boolean add(RequestableInterface r) throws BPJRequestableSetException {
		throw new BPJRequestableSetException();

	}

	public boolean isEvent() {
		return true;
	}

	public int size() {
		return 1;
	}

	public BEvent getEvent() throws BPJRequestableSetException {

		return this;
	}
	public ArrayList<BEvent> getEventList() {
		ArrayList<BEvent> list = new ArrayList<BEvent>();
		this.addEventsToList(list);
		return list;
	}

	public void addEventsToList(ArrayList<BEvent> list) {
		list.add(this); 
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BEvent other = (BEvent) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(BEvent e) {
		return name.compareTo(e.getName());
	}
	
	
	
}

/**
 * An iterator over a single event object. Allows to view an event as a
 * (singleton) set.
 */
class SingleEventIterator implements Iterator<RequestableInterface> {
	BEvent e;

	public SingleEventIterator(BEvent e) {
		this.e = e;
	}

	@Override
	public boolean hasNext() {
		return e != null;
	}

	@Override
	public BEvent next() {
		BEvent tmp = e;
		e = null;
		return tmp;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
