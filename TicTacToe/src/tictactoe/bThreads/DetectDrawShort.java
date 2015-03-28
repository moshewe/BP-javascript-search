package tictactoe.bThreads;

import static bp.eventSets.EventSetConstants.none;
import static tictactoe.events.StaticEvents.draw;
import static tictactoe.events.StaticEvents.gameOver;
import tictactoe.events.Move;
import bp.eventSets.EventSet;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJException;

public class DetectDrawShort extends DetectDraw {

	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);

		// Wait for 9 events
		for (int count = 0; count < 8; count++) {
			bsync(none, new EventsOfClass(Move.class), none);
		}
		bsync(draw, none, none);
	}

}
