package bp.search;

import aima.core.agent.Action;
import bp.BEvent;
import bp.BProgram;
import bp.BThread;

import java.io.IOException;

public class BPAction implements Action, Comparable<BPAction> {

    private final BEvent ev;

    public BPAction(BEvent ev) {
        this.ev = ev;
    }

    public boolean isNoOp() {
        return false;
    }

    public BEvent getEvent() {
        return ev;
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
        bplog("APPLYING " + ev);
        // List<BTState> btStates = new ArrayList<BTState>();
        BProgram bp = bps.getBp();
        BEvent event = ev.getEvent();
        bp.setLastEvent(event);
        BPState newBps = bps.copy();
        for (BTState bts : newBps.getBTstates()) {
            // if (bts.bt instanceof UpdateDisplay) {
            // log("inspecting update display");
            // }
            // if (bts.bt instanceof EnforceTurns) {
            // log("inspecting enforce turns");
            // }
            // if (bts.bt instanceof DetectDraw) {
            // log("inspecting enforce turns");
            // }
            bts.restore();
            if (bts.watchedEvents.contains(event)
                    || bts.requestedEvents.contains(event)) {
                BThread bt = bts.bt;
                bt.resume(event);
                bts._cont = bt.getCont();
                bts.requestedEvents = bt.getRequestedEvents();
                bts.watchedEvents = bt.getWaitedEvents();
                bts.blockedEvents = bt.getBlockedEvents();
                // if (bt.isFinished()) {
                // log(bt + " is finished!");
                // // finishedBThreads.add(bts);
                // }
            }

            // btStates.add(bts);
        }

        // bps.getBTstates().removeAll(finishedBThreads);
        bplog("DONE APPLYING " + ev);
        return newBps;
    }

    @Override
    public int compareTo(BPAction other) {
        return ev.compareTo(other.getEvent());
    }

    @Override
    public boolean equals(Object obj) {
        if (!getClass().isInstance(obj)) {
            return false;
        }

        BPAction otherActions = (BPAction) obj;
        return ev.equals(otherActions);
    }

    protected void bplog(String string) {
        System.out.println("[" + this + "]: " + string);
    }

    @Override
    public String toString() {
        return ev.toString();
    }

}
