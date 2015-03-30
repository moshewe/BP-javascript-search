package bp.search;

import bp.BThread;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import org.mozilla.javascript.ContinuationPending;

/**
 * A class for capturing be-thread states.
 */
public class BTState {

    /**
     * The be-thread whose state is captured
     */
    public final BThread bt;

    /**
     * Continuation object.
     */
    protected ContinuationPending _cont;

    /**
     * Temporary storage for bpSync parameters
     */
    public transient RequestableInterface requestedEvents;
    public transient EventSetInterface watchedEvents;
    public transient EventSetInterface blockedEvents;

    public BTState(BThread bt) {
        this.bt = bt;
        this.requestedEvents = bt.getRequestedEvents();
        this.watchedEvents = bt.getWaitedEvents();
        this.blockedEvents = bt.getBlockedEvents();
        this._cont = bt.getCont();
    }

    public BTState(BTState other) {
        this.bt = other.bt;
        this.requestedEvents = other.requestedEvents;
        this.watchedEvents = other.watchedEvents;
        this.blockedEvents = other.blockedEvents;
        this._cont = other._cont;
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
        bt.setWaitedEvents(watchedEvents);
        bt.setBlockedEvents(blockedEvents);
        bt.setCont(_cont);
        bt.revive();
//		System.out.println("restored " + bt);
    }

    public ContinuationPending getCont() {
        return _cont;
    }

    public BTState copy() {
        return new BTState(this);
    }

}