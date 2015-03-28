package tictactoe.search;

import aima.core.search.framework.EvaluationFunction;
import aima.core.search.framework.Node;
import bp.BEvent;
import bp.BProgram;
import bp.search.BPState;
import tictactoe.events.StaticEvents;

import java.io.Serializable;

public class TTTEvaluationFunction implements EvaluationFunction, Serializable {

	public double f(Node n) {
		BPState bps = (BPState) n.getState();
		BProgram program = bps.getBp();
		BEvent lastEvent = program.getLastEvent();
		if (lastEvent == null) {
			return 0;
			// throw new BPJException("no event triggered!");
		}

		if (lastEvent.equals(StaticEvents.OWin)) {
			return 100;
		}

		if (lastEvent.equals(StaticEvents.XWin)) {
			return -100;
		}

		return 0;
	}

}
