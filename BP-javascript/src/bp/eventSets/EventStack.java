package bp.eventSets;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by orelmosheweinstock on 5/26/15.
 */
public class EventStack extends Stack<EventSetInterface>
        implements EventSetInterface {

    protected String _name;

    public EventStack(EventSetInterface... esis) {
        super();

        for (EventSetInterface esi : esis) {
            push(esi);
        }
    }

    public EventStack(String _name, EventSetInterface... esis) {
        this(esis);
        this._name = _name;
    }

    @Override
    public boolean contains(Object o) {
        Iterator<EventSetInterface> itr = this.iterator();

        while (itr.hasNext()) {
            EventSetInterface eSetInterface = itr.next();
            if (eSetInterface.contains(o)) {
                return true;
            }
        }

        return false;
    }

    public String toString() {
        if (_name != null) {
            return _name;
        } else {
            return super.toString();
        }
    }

}
