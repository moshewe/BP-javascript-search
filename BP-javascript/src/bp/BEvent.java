package bp;

import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import bp.exceptions.BPJRequestableSetException;

import java.util.ArrayList;
import java.util.Iterator;

import static bp.BProgramControls.debugMode;

/**
 * A base class for events
 */
@SuppressWarnings("serial")
public class BEvent implements EventSetInterface,
        RequestableInterface, Comparable<BEvent> {

    protected String _name = this.getClass().getSimpleName();
    protected boolean _outputEvent = false;

    public BEvent(String name, boolean outputEvent) {
        _name = name;
        _outputEvent = outputEvent;
    }

    public BEvent() {
    }

    public BEvent(String _name) {
        this._name = _name;
    }

    public boolean isOutputEvent() {
        return _outputEvent;
    }

    @Override
    public boolean contains(Object o) {
        return this.equals(o);
    }

    public Iterator<RequestableInterface> iterator() {
        return new SingleEventIterator(this);
    }

    public String toString() {
        return _name;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
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
        ArrayList<BEvent> list = new ArrayList<BEvent>(1);
        list.add(this);
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BEvent other = (BEvent) obj;
        return _name.equals(other.getName());
    }

    @Override
    public int compareTo(BEvent e) {
        return _name.compareTo(e.getName());
    }

    protected void bplog(String string) {
        if (debugMode)
            System.out.println(this + ": " + string);
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
