package bp.search.goaltest;

import java.util.Arrays;
import java.util.List;

import aima.core.search.framework.GoalTest;
import bp.BEvent;
import bp.search.BPState;

public class EventTriggeredGoalTest implements GoalTest {

	private List<BEvent> goalEvents;
	
	public EventTriggeredGoalTest(BEvent... winEvents) {
		goalEvents = Arrays.asList(winEvents);
	}

	public boolean isGoalState(Object state) {
		BPState bps = (BPState) state;
		return goalEvents.contains(bps.getBp().getLastEvent());
	}

}
