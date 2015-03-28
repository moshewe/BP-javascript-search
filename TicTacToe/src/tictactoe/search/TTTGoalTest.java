package tictactoe.search;

import aima.core.search.framework.GoalTest;
import bp.BEvent;
import bp.BProgram;
import bp.search.BPState;
import bp.search.BTState;
import tictactoe.bThreads.EnforceTurns;
import tictactoe.events.StaticEvents;

import static tictactoe.events.StaticEvents.OEvents;
import static tictactoe.events.StaticEvents.XEvents;

public class TTTGoalTest implements GoalTest {

	public boolean isGoalState(Object state) {
		BPState bps = (BPState) state;
		BProgram program = bps.getBp();
		BEvent lastEvent = program.getLastEvent();
		if (lastEvent == null) {
			return false;
		}

		BEvent winEvent = null;
		for (BTState bts : bps.getBTstates()) {
			// can be avoided by writing a specific program object for TTT
			if (bts.bt instanceof EnforceTurns) {
				if (bts.bt.getWaitedEvents() == XEvents) {
					winEvent = StaticEvents.XWin;
				}
				if (bts.bt.getWaitedEvents() == OEvents) {
					winEvent = StaticEvents.OWin;
				}
				break;
			}
		}

		if (lastEvent.equals(winEvent)) {
			return true;
		} else {
			return false;
		}

	}

}
