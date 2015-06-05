package bp.search.adversarial;

import aima.core.search.framework.Metrics;
import bp.Arbiter;
import bp.BEvent;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.adversarial.players.BPSystemPlayer;

/**
 * @author moshewe
 */
public class AdversarialSearchArbiter extends Arbiter {

    protected BPAdversarialSearch _algorithm;
    protected BPGame game;

    public AdversarialSearchArbiter(BPAdversarialSearch algorithm, BPGame game) {
        this._algorithm = algorithm;
        this.game = game;
    }

    @Override
    protected BEvent nextEvent() {
        BPState bps = new BPState(getProgram());
        BPPlayer player = game.getPlayer(bps);
        if (player instanceof BPSystemPlayer) {
            return makeDecision();
        } else {
            return super.nextEvent();
        }
    }

    public BEvent makeDecision() {
        BPState initialState = game.getInitialState();
        bplog("=== STARTING SEARCH ===");
        BPAction decision = _algorithm.makeDecision(initialState);
        BEvent choice;
        if (decision == null) {
            bplog("all decisions are value=-inf! chosen at random...");
            choice = null;
        } else {
            choice = decision.getEvent();
        }
        bplog("=== SEARCH FINISHED ===");
        bplog("== METRICS ==");
        Metrics metrics = _algorithm.getMetrics();
        for (String k : metrics.keySet()) {
            bplog(k + " = " + metrics.get(k));
        }
        bplog("== END OF METRICS ==");

        initialState.restore();
        return choice;
    }

}