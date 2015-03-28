package bp.search.adversarial;

import bp.Arbiter;
import bp.BEvent;
import bp.search.BPAction;
import bp.search.BPState;

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
            return null;
        } else {
            return super.nextEvent();
        }
    }

    protected BEvent getExternalEvent() {
        if (gameOn) {
            return null;
        } else {
//            return super.getExternalEvent();
            return null;
        }
    }

    public BEvent makeDecision() {
        BPState state = game.getInitialState();
        gameOn = true;
        BPAction decision = algorithm.makeDecision(state);
        BEvent choice;
        if (decision == null) {
            bplog("all decisions are value=-inf! chosen at random...");
            choice = null;
        } else {
            choice = decision.getEvent();
        }

        state.restore();
        gameOn = false;
        return choice;
    }

}