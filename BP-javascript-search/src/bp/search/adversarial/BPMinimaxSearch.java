package bp.search.adversarial;

import aima.core.search.adversarial.MinimaxSearch;
import bp.Arbiter;
import bp.search.BPAction;
import bp.search.BPState;

public class BPMinimaxSearch extends MinimaxSearch<BPState, BPAction, BPPlayer> {

    public BPMinimaxSearch(BPGame game) {
        super(game);
    }

}
