package bp.search;

import bp.BThread;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import bp.search.bthreads.SimulatorBThread;
import org.mozilla.javascript.ContinuationPending;

/**
 * A class for capturing be-thread states.
 */
public class BTState {

    /**
     * The be-thread whose state is captured
     */
    public final BThread bt;
    public boolean simMode;

    /**
     * Continuation object.
     */
    protected ContinuationPending cont;

    /**
     * Temporary storage for bpSync parameters
     */
    public transient RequestableInterface requestedEvents;
    public transient EventSetInterface waitedEvents;
    public transient EventSetInterface blockedEvents;

    public BTState(BThread bt) {
        this.bt = bt;
        this.requestedEvents = bt.getRequestedEvents();
        this.waitedEvents = bt.getWaitedEvents();
        this.blockedEvents = bt.getBlockedEvents();
        this.cont = bt.getCont();
        simMode = false;
    }

    // need to implement visitor someday
    public BTState(SimulatorBThread bt){
        this((BThread) bt);
        this.simMode = bt._simMode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(bt);
        sb.append("\n");
        for (int i = 0; i < bt.toString().length(); i++) {
            sb.append("-");
        }
        sb.append("\n");
        sb.append("R=" + bt.getRequestedEvents());
        sb.append("\n");
        sb.append("W=" + bt.getWaitedEvents());
        sb.append("\n");
        sb.append("B=" + bt.getBlockedEvents());
        sb.append("\n");
        return sb.toString();
    }

    public void restore() {
        bt.setRequestedEvents(requestedEvents);
        bt.setWaitedEvents(waitedEvents);
        bt.setBlockedEvents(blockedEvents);
        bt.setCont(cont);
        bt.revive();
        //need to implement visitor someday
        if(simMode){
            ((SimulatorBThread) bt)._simMode = simMode;
            bplog("restoring " + bt + " simMode to " + simMode);
        }
//		System.out.println("restored " + bt);
    }

    private void bplog(String s) {
        System.out.println("[BTState]: ");
    }

    public ContinuationPending getCont() {
        return cont;
    }

}