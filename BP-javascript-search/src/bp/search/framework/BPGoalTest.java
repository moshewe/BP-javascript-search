package bp.search.framework;

import aima.core.search.framework.GoalTest;
import bp.search.BPState;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public abstract class BPGoalTest implements GoalTest {

    public abstract boolean isGoalState(BPState state);

    @Override
    public boolean isGoalState(Object state) {
        return isGoalState((BPState) state);
    }
}
