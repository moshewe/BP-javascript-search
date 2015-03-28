package tictactoe.bThreads;

import static bp.eventSets.EventSetConstants.none;
import static tictactoe.events.StaticEvents.gameOver;
import tictactoe.events.Click;
import tictactoe.events.X;
import bp.BProgram;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJException;

/**
 * BThread that fires X when a button is clicked (in response to a P event)
 */
public class UserMove extends BThread {

	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);
		BProgram bp = getBProgram();

		while (true) {
			// Wait for a P event

			bsync(none,new EventsOfClass(Click.class),none);

			// Put an X
			Click p = (Click) bp.getLastEvent();
			bsync(new X(p.row, p.col),none,none);

		}
	}

}