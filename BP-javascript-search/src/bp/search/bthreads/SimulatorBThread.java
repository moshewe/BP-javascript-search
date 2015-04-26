package bp.search.bthreads;

import bp.BEvent;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import bp.search.events.SimStartEvent;
import org.mozilla.javascript.Function;

import static bp.eventSets.EventSetConstants.none;

/**
 * Created by orelmosheweinstock on 4/25/15.
 */
public class SimulatorBThread extends BThread {

    private boolean simMode = false;

    public SimulatorBThread(String name, Function func) {
        super(name, func);
    }

    @Override
    public BEvent bsync(RequestableInterface requestedEvents,
                        EventSetInterface waitedEvents, EventSetInterface blockedEvents) {
//        bplog("in simulation bsync");
        if (simMode) {
            return super.bsync(requestedEvents, waitedEvents, blockedEvents);
        } else {
            EventSet eset = new EventSet(requestedEvents, waitedEvents, SimStartEvent.getInstance());
            BEvent ev = super.bsync(none, eset, blockedEvents);
            if (ev == SimStartEvent.getInstance()) {
                bplog("going into simulation mode!");
                simMode = true;
                return bsync(requestedEvents, waitedEvents, blockedEvents);
            }
            return ev;
        }
    }
}
