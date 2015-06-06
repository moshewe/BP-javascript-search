package bp.search.informed;

import aima.core.agent.Action;
import aima.core.search.framework.Node;
import aima.core.search.framework.Problem;
import aima.core.search.framework.QueueSearch;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.framework.BPNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public class BPQueueSearch extends QueueSearch {

    @Override
    public List<Node> getResultingNodesToAddToFrontier(Node nodeToExpand, Problem p) {
        List<Node> ans = new ArrayList<>();
            BPState bpState = (BPState) nodeToExpand.getState();
        for (Action action : p.getActionsFunction().actions(bpState)) {
            BPAction bpAction = (BPAction) action;
            BPState next = bpAction.apply(bpState);
            double stepCost = p.getStepCostFunction().c(bpState, action, next);
            ans.add(new BPNode(next, nodeToExpand, bpAction, stepCost));
        }

        return ans;
    }
}
