package bp.search.adversarial;

import aima.core.search.adversarial.AlphaBetaSearch;
import bp.search.BPAction;
import bp.search.BPState;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public class BPAlphaBetaSearch extends AlphaBetaSearch<BPState, BPAction, BPPlayer>
        implements BPAdversarialSearch {

    public BPAlphaBetaSearch(BPGame game) {
        super(game);
    }
}
