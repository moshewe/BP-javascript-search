package bpbwapi;

import bp.BPApplication;
import bp.search.BPState;
import bp.search.adversarial.BPGame;
import bp.search.adversarial.BPPlayer;

/**
 * Created by Orel on 23/08/2015.
 */
public class BWGame extends BPGame {

    public BWGame(BPApplication bp) {
        super(bp);
    }

    @Override
    public boolean isTerminal(BPState bpState) {
        return false;
    }

    @Override
    public double getUtility(BPState bpState, BPPlayer bpPlayer) {
        return 0;
    }
}
