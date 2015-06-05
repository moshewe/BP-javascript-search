package bp.search.framework;

import aima.core.search.framework.*;
import bp.search.BPState;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public class BPProblem extends Problem {

    public BPProblem(BPState initialState,
                     BPActionsFunction actionsFunction,
                     BPResultFunction resultFunction,
                     BPGoalTest goalTest) {
        super(initialState, actionsFunction, resultFunction, goalTest);
    }

    public BPProblem(BPState initialState,
                     BPActionsFunction actionsFunction,
                     BPResultFunction resultFunction,
                     BPGoalTest goalTest,
                     BPStepCostFunction stepCostFunction) {
        super(initialState, actionsFunction, resultFunction, goalTest,
                stepCostFunction);
    }

    @Override
    public BPActionsFunction getActionsFunction() {
        return (BPActionsFunction) super.getActionsFunction();
    }
}
