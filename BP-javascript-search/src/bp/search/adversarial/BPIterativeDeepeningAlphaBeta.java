package bp.search.adversarial;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import bp.search.BPAction;
import bp.search.BPState;

/**
 * Created by Orel on 23/08/2015.
 */
public class BPIterativeDeepeningAlphaBeta extends IterativeDeepeningAlphaBetaSearch<BPState, BPAction, BPPlayer> {

    public BPIterativeDeepeningAlphaBeta(BPGame game, double utilMin, double utilMax, int time) {
        super(game, utilMin, utilMax, time);
    }
}
