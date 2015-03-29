package bp.search.adversarial;

import bp.Arbiter;
import bp.BEvent;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.adversarial.players.BPSystemPlayer;

import java.io.IOException;

/**
 * @author moshewe
 */
public class MinimaxSearchArbiter extends Arbiter {

    protected BPMinimaxSearch algorithm;
    protected BPGame game;
    public boolean gameOn;

    public MinimaxSearchArbiter(BPMinimaxSearch algorithm, BPGame game) {
        this.algorithm = algorithm;
        this.game = game;
    }

    @Override
    protected BEvent nextEvent() {
        if (gameOn) {
            return makeDecision();
        } else {
            BPState bps = new BPState(getProgram());
            BPPlayer player = game.getPlayer(bps);
            if (player instanceof BPSystemPlayer) {
                return makeDecision();
            } else {
                return super.nextEvent();
            }
        }
    }

    public BEvent makeDecision() {
        BPState initialState = game.getInitialState();
        gameOn = true;
        bplog("=== STARTING SEARCH ===");
        BPAction decision = algorithm.makeDecision(initialState);
        BEvent choice;
        if (decision == null) {
            bplog("all decisions are value=-inf! chosen at random...");
            choice = null;
        } else {
            choice = decision.getEvent();
        }
        bplog("=== SEARCH FINISHED ===");

        initialState.restore();
        gameOn = false;
        return choice;
    }

}