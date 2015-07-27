package bp.search;

import aima.core.agent.Action;
import bp.BEvent;
import bp.BPApplication;
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
        bplog("APPLYING " + _ev);
        bps.restore();
        bplog("BEFORE: " + bps.toString());
        BPState newBps = bps.copy();
        BPApplication bp = newBps.getProgram();
        bp.eventLog.add(_ev);
        newBps.eventLog.add(_ev);
        for (BTState bts : newBps.getBTstates()) {
            if (bts.waitedEvents.contains(_ev)
                    || bts.requestedEvents.contains(_ev)) {
                BThread bt = bts.bt;
                bt.resume(_ev);
                bts.cont = bt.getCont();
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
