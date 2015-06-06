package bp.search.adversarial;

import aima.core.search.adversarial.MinimaxSearch;
import bp.search.BPAction;
import bp.search.BPState;

public class BPMinimaxSearch extends MinimaxSearch<BPState, BPAction, BPPlayer>
        implements BPAdversarialSearch {

    public BPMinimaxSearch(BPGame game) {
        super(game);
    }

}
