package tictactoe.bThreads;

import bp.BThread;
import bp.exceptions.BPJRequestableSetException;
import tictactoe.events.Move;

import static bp.eventSets.EventSetConstants.none;

public class BlockMiddle extends BThread {

	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		bsync(none, none, new Move(1, 1));
	}

}
