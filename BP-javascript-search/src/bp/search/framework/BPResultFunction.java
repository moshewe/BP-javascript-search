package bp.search.framework;

import aima.core.agent.Action;
import aima.core.search.framework.ResultFunction;
import bp.search.BPAction;
import bp.search.BPState;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public class BPResultFunction implements ResultFunction {

    public BPState result(BPState state, BPAction action){
        return action.apply(state);
    }

    @Override
    public Object result(Object s, Action a) {
        return result((BPState) s, (BPAction) a);
    }
}
