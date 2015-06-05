package bp.search.informed;

import aima.core.agent.Action;
import aima.core.search.framework.Node;
import aima.core.search.framework.Problem;
import aima.core.search.framework.QueueSearch;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.framework.BPNode;
import bp.search.framework.BPProblem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public class BPQueueSearch extends QueueSearch {

    public List<Node> getResultingNodesToAddToFrontier(BPNode nodeToExpand, BPProblem p) {
        List<Node> ans = new ArrayList<>();
        for (Action action : p.getActionsFunction().actions(nodeToExpand)) {
            BPAction bpAction = (BPAction) action;
            BPState next = bpAction.apply(nodeToExpand.getState());
            double stepCost = p.getStepCostFunction().c(nodeToExpand.getState(), action, next);
            ans.add(new BPNode(next, nodeToExpand, bpAction, stepCost));
        }

        return ans;
    }

    @Override
    public List<Node> getResultingNodesToAddToFrontier(Node nodeToExpand, Problem p) {
        return getResultingNodesToAddToFrontier((BPNode) nodeToExpand, (BPProblem) p);
    }
}
