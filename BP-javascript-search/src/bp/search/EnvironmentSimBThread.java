package bp.search;

import bp.BEvent;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.eventSets.EventSetConstants;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;

/**
 * A BThread that simulates user behaviour. MUST be used in search-based
 * arbiting.
 * 
 * @author moshewe
 * 
 */
public abstract class EnvironmentSimBThread extends BThread {

	protected boolean simMode = false;
	/**
	 * An event that when fired causes the simulation bthread to watch in bsync
	 * both required and watched events. This way it can advance its simulation
	 * algorithm without simulating external input, allowing for actual external
	 * output to be fired.
	 */
	public static final BEvent simOff = new BEvent("sandboxOff");
	/**
	 * An event that when fired causes the simulation bthread to require in
	 * bsync events as if it were external input.
	 */
	public static final BEvent simOn = new BEvent("sandboxOn");

	public static final EventSet simEvents = new EventSet(simOn, simOff);

	@Override
	public synchronized void bsync(RequestableInterface requestedEvents,
								   EventSetInterface waitedEvents, EventSetInterface blockedEvents) {
		if (simMode) {
			super.bsync(requestedEvents,
					new EventSet(waitedEvents, simEvents), blockedEvents);
		} else {
			super.bsync(EventSetConstants.none, new EventSet(requestedEvents,
					waitedEvents, simEvents), blockedEvents);
		}

		if (bp.getLastEvent() == simOn) {
			bplog("sim mode turned on");
			simMode = true;
			bsync(requestedEvents, waitedEvents, blockedEvents);
		} else if (bp.getLastEvent() == simOff) {
			bplog("sim mode turned off");
			simMode = false;
			bsync(requestedEvents, waitedEvents, blockedEvents);
		}

	}

}
