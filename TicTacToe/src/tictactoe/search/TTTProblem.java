package tictactoe.search;

import bp.search.BPState;
import bp.search.framework.*;

/**
 * Created by orelmosheweinstock on 6/6/15.
 */
public class TTTProblem extends BPProblem {

    public TTTProblem(BPState initialState,
                      BPActionsFunction actionsFunction,
                      BPResultFunction resultFunction,
                      BPGoalTest goalTest) {
        super(initialState, actionsFunction, resultFunction, goalTest);
    }

    public TTTProblem(BPState initialState,
                      BPActionsFunction actionsFunction,
                      BPResultFunction resultFunction,
                      BPGoalTest goalTest,
                      BPStepCostFunction stepCostFunction) {
        super(initialState, actionsFunction, resultFunction, goalTest,
                stepCostFunction);
    }
}
