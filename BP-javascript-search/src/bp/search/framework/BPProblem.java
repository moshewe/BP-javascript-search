package bp.search.framework;

import aima.core.search.framework.Problem;
import bp.search.BPState;

import static bp.BProgramControls.debugMode;

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

    @Override
    public BPState getInitialState() {
        return (BPState) super.getInitialState();
    }

    protected void bplog(String s) {
        if (debugMode)
            System.out.println("[" + this + "]: " + s);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
