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
public class MinimaxSearchArbiter extends Arbiter {

    protected BPMinimaxSearch algorithm;
    protected BPGame game;
//    public boolean gameOn;

    public MinimaxSearchArbiter(BPMinimaxSearch algorithm, BPGame game) {
        this.algorithm = algorithm;
        this.game = game;
    }

//    public MinimaxSearchArbiter(BPMinimaxSearch algorithm, BPGame game) {
//        this.algorithm = algorithm;
//        this.game = game;
//        _simBThreads = new ArrayList<>();
//    }

    @Override
    protected BEvent nextEvent() {
//        if (gameOn) {
//            return makeDecision();
//        } else {
////            BPState bps = new BPState(getProgram());
        BPPlayer player = game.getPlayer(null);
        if (player instanceof BPSystemPlayer) {
            return makeDecision();
        } else {
            return super.nextEvent();
        }
//        }
    }

    public BEvent makeDecision() {
        BPState initialState = game.getInitialState();
//        addSimBThreads(initialState);
//        gameOn = true;
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
        bplog("== METRICS ==");
        Metrics metrics = algorithm.getMetrics();
        for (String k : metrics.keySet()) {
            bplog(k + " = " + metrics.get(k));
        }
        bplog("== END OF METRICS ==");

//        removeSimBThreads(initialState);
        initialState.restore();
//        gameOn = false;
        return choice;
    }

//    private void removeSimBThreads(BPState initialState) {
//        initialState.bp.getBThreads().removeAll(_simBThreads);
//        for (BThread sim : _simBThreads) {
//            initialState.getBTstates().add(new BTState(sim));
//        }
//    }
//
//    private void addSimBThreads(BPState initialState) {
//        initialState.bp.getBThreads().addAll(_simBThreads);
//        for (BThread sim : _simBThreads) {
//            initialState.getBTstates().add(new BTState(sim));
//        }
//    }

}