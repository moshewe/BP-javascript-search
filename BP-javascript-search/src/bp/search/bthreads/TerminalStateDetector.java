package bp.search.bthreads;

import static bp.eventSets.EventSetConstants.none;
import bp.BThread;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJRequestableSetException;
import bp.search.events.TerminalEvent;

/**
 * A control bthread used by the search engine for cutting search branches. A
 * branch is cut when a terminal event is detected.
 * 
 * @author moshewe
 * 
 */
public class TerminalStateDetector extends BThread {

	private boolean terminal = false;

	public boolean isTerminal() {
		return terminal;
	}

	@Override
	public void setupScope() {

	}

//	@Override
//	public void body() throws InterruptedException,
//			BPJRequestableSetException {
//		bsync(none, new EventsOfClass(TerminalEvent.class), none);
//		terminal = true;
//	}

}
