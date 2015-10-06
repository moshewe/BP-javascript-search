package bp.eventSets;

import bp.BEvent;
import bp.exceptions.BPJRequestableSetException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("serial")
public class RequestableEventSet extends ArrayList<RequestableInterface>
        implements EventSetInterface, RequestableInterface, Serializable {

    private String name = this.getClass().getSimpleName();

    public RequestableEventSet(RequestableInterface... reqs) {
        super(reqs.length);
        for (RequestableInterface r : reqs) {
            add(r);
        }
    }

    public RequestableEventSet(String name, RequestableInterface... reqs) {
        this(reqs);
        this.setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public boolean contains(Object o) {
        for (RequestableInterface r : this)
            if (r.contains(o))
                return true;
        return false;
    }

}
