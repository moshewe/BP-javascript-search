package bp.eventSets;

import bp.BEvent;
import bp.exceptions.BPJRequestableSetException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An interface for what can be requested in bSync call. Currently only facades
 * Iterable<Event>, future implementations may have complex iterators
 * 
 */
public interface RequestableInterface extends Iterable<RequestableInterface>, EventSetInterface {

	public Iterator<RequestableInterface> iterator();

	public RequestableInterface get(int index);

	public boolean isEvent();

	public int size();

	public boolean contains(Object o);

	public BEvent getEvent() throws BPJRequestableSetException;
	
	public ArrayList<BEvent> getEventList();
		
	public void addEventsToList(ArrayList<BEvent> list);
}
