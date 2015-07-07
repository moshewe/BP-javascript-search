package bp.search.informed;

import aima.core.agent.Action;
import aima.core.search.framework.Search;
import bp.Arbiter;
import bp.BEvent;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.framework.BPActionsFunction;
import bp.search.framework.BPGoalTest;
import bp.search.framework.BPProblem;
import bp.search.framework.BPResultFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orelmosheweinstock on 6/6/15.
 */
public class InformedSearchArbiter extends Arbiter {

    protected Search _algorithm;
    protected BPGoalTest _goal;
    protected BPActionsFunction _actions = new BPActionsFunction();
    protected BPResultFunction _result = new BPResultFunction();
    protected InitStateGenerator _generator;

    public InformedSearchArbiter(Search algorithm,
                                 InitStateGenerator generator,
                                 BPGoalTest goal) {
        _algorithm = algorithm;
        _goal = goal;
        _generator = generator;
    }

    @Override
    public BEvent nextEvent() {
        List<Action> res = new ArrayList<>();
        BPState bps = _generator.genInitState();
        BPProblem prob = new BPProblem(bps,
                _actions, _result, _goal);
        try {
            res = _algorithm.search(prob);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (res.isEmpty()) {
            return null;
        } else {
//            return the first action to take to the _goal
            BPAction action = (BPAction) res.get(0);
            return action.getEvent();
        }
    }

}
