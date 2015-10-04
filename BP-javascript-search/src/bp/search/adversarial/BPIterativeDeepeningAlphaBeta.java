package bp.search.adversarial;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import bp.Arbiter;
import bp.search.BPAction;
import bp.search.BPState;

/**
 * Created by Orel on 23/08/2015.
 */
public class BPIterativeDeepeningAlphaBeta extends Arbiter {

    IterativeDeepeningAlphaBetaSearch<BPState, BPAction, BPPlayer> _algorithm;

    public BPIterativeDeepeningAlphaBeta(BPGame game, double utilMin, double utilMax, int time) {
        _algorithm = new IterativeDeepeningAlphaBetaSearch(
                game, utilMin, utilMax, time);
    }
}
