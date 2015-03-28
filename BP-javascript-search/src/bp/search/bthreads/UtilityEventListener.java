package bp.search.bthreads;

import static bp.eventSets.EventSetConstants.none;
import bp.BThread;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJRequestableSetException;

public class UtilityEventListener extends BThread {

	private double utility;

//	@Override
//	public void body() throws InterruptedException,
//			BPJRequestableSetException {
//		while (true) {
//			bsync(none, new EventsOfClass(UtilityEvent.class), none);
//			UtilityEvent last = (UtilityEvent) bp.getLastEvent();
//			utility += last.getValue();
//		}
//	}

	public double getUtility() {
		return utility;
	}

}
