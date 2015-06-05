package bp.search.framework;

import aima.core.search.framework.Node;
import bp.search.BPAction;
import bp.search.BPState;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public class BPNode extends Node {

    public BPNode(BPState state) {
        super(state);
    }

    public BPNode(BPState state, BPNode parent, BPAction action,
                  double stepCost) {
        super(state, parent, action, stepCost);
    }

    @Override
    public BPState getState() {
        return (BPState) super.getState();
    }

    @Override
    public BPNode getParent() {
        return (BPNode) super.getParent();
    }
}
