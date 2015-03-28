package tictactoe.bThreads;

import static bp.eventSets.EventSetConstants.none;
import tictactoe.events.Move;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

public class BlockMiddle extends BThread {

	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		bsync(none, none, new Move(1, 1));
	}

}
