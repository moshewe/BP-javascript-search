package bp.search.framework;

import aima.core.agent.Action;
import aima.core.search.framework.StepCostFunction;
import bp.search.BPAction;
import bp.search.BPState;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public class BPStepCostFunction implements StepCostFunction {

    public double c(BPState s, BPAction a, BPState sDelta) {
        return 1;
    }

    @Override
    public double c(Object s, Action a, Object sDelta) {
        return c((BPState) s, (BPAction) a, (BPState) sDelta);
    }
}
