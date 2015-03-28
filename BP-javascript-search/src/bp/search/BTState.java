package bp.search;

import bp.BThread;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import org.mozilla.javascript.ContinuationPending;

/**
 * 
 * A class for capturing be-thread states.
 * 
 */
public class BTState {

	/** The be-thread whose state is captured */
	public final BThread bt;

	/**
	 * Object storing the continuation object in case of shallow continuation
	 * (otherwise, a full serialization of the continuation is stored in the
	 * variable serialization and this variable is not used.
	 */
	protected ContinuationPending cont;

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
		this.cont = bt.getCont();
	}

	// public BTState(BThread bt, Continuation cont) {
	// this.bt = bt;
	// this.cont = cont;
	// save(cont);
	// BThreadContinuationContext context = (BThreadContinuationContext) cont
	// .value();
	// this.requestedEvents = context.requestedEvents;
	// this.watchedEvents = context.watchedEvents;
	// this.blockedEvents = context.blockedEvents;
	// }
	//
	public BTState(BTState other) {
		this.bt = other.bt;
		this.requestedEvents = other.requestedEvents;
		this.watchedEvents = other.watchedEvents;
		this.blockedEvents = other.blockedEvents;
		this.cont = other.cont;
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
		bt.setCont(cont);
	}

	public ContinuationPending getCont() {
		return cont;
	}

	public BTState copy() {
		return new BTState(this);
	}

}