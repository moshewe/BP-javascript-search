package bp.search.bthreads;

import bp.BEvent;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import bp.search.events.SimEndEvent;
import bp.search.events.SimStartEvent;

/**
 * Created by orelmosheweinstock on 4/9/15.
 */
public class SimulatorBThread extends BThread {

    public boolean simulationMode = false;

    public SimulatorBThread(String source) {
        super(source);
    }

    @Override
    public BEvent bsync(RequestableInterface requestedEvents, EventSetInterface waitedEvents, EventSetInterface blockedEvents) {
        EventSetInterface simWaitedEvents = new EventSet(waitedEvents, new SimStartEvent());
        if (simulationMode) {
            BEvent ev = super.bsync(requestedEvents, simWaitedEvents, blockedEvents);
            if (ev instanceof SimEndEvent) {
                simulationMode = false;
            }
            return bsync(requestedEvents, simWaitedEvents, blockedEvents);
        } else {
            BEvent ev = super.bsync(requestedEvents, simWaitedEvents, blockedEvents);
            if (ev instanceof SimStartEvent) {
                bplog("received simStart, switching to simulation mode!");
                simulationMode = true;
            }
            return bsync(requestedEvents, simWaitedEvents, blockedEvents);
        }
    }
}
