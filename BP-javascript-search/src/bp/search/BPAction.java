package bp.search;

import aima.core.agent.Action;
import bp.BEvent;
import bp.BProgram;
import bp.BThread;

import java.io.IOException;

import static bp.BProgramControls.debugMode;

public class BPAction implements Action, Comparable<BPAction> {

    private final BEvent _ev;

    public BPAction(BEvent ev) {
        this._ev = ev;
    }

    public boolean isNoOp() {
        return false;
    }

    public BEvent getEvent() {
        return _ev;
    }

    /**
     * Returns a new BPState representing the given state after application of
     * this action.
     *
     * @param bps
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public BPState apply(BPState bps) {
        bps.restore();
        bplog("APPLYING " + _ev);
        BProgram bp = bps.getProgram();
        bp.eventLog.add(_ev);
        BPState newBps = bps.copy();
        bplog("BEFORE: " + bps.toString());
        for (BTState bts : newBps.getBTstates()) {
            if (bts.waitedEvents.contains(_ev)
                    || bts.requestedEvents.contains(_ev)) {
                BThread bt = bts.bt;
                bt.resume(_ev);
                bts._cont = bt.getCont();
                bts.requestedEvents = bt.getRequestedEvents();
                bts.waitedEvents = bt.getWaitedEvents();
                bts.blockedEvents = bt.getBlockedEvents();
            }
        }

        bplog("DONE APPLYING " + _ev + ", NEW STATE IS:");
        bplog(newBps.toString());
        return newBps;
    }

    @Override
    public int compareTo(BPAction other) {
        return _ev.compareTo(other.getEvent());
    }

    @Override
    public boolean equals(Object obj) {
        if (!getClass().isInstance(obj)) {
            return false;
        }

        BPAction otherActions = (BPAction) obj;
        return _ev.equals(otherActions);
    }

    protected void bplog(String string) {
        if (debugMode)
            System.out.println("Action[" + this + "]: " + string);
    }

    @Override
    public String toString() {
        return _ev.toString();
    }

}
