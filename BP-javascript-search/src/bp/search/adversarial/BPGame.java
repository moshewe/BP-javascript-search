package bp.search.adversarial;

import aima.core.search.adversarial.Game;
import bp.BEvent;
import bp.BPApplication;
import bp.eventSets.RequestableInterface;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.BTState;
import bp.search.adversarial.players.BPSystemPlayer;
import bp.search.adversarial.players.EnvironmentPlayer;
import bp.search.informed.BPJSHeuristicFunction;

import java.util.ArrayList;
import java.util.List;

import static bp.BProgramControls.debugMode;

public abstract class BPGame implements Game<BPState, BPAction, BPPlayer> {

    protected static BPPlayer[] _players = {BPSystemPlayer.instance,
            EnvironmentPlayer.instance};
    protected BPApplication _program;
    protected BPJSHeuristicFunction _heuristic;
    protected BPPlayer _lastPlayer;

    protected double getUtilityFromHeuristics(BPState state, BPPlayer player) {
        if (player == BPSystemPlayer.instance)
            return _heuristic.h(state);
        else
            return -_heuristic.h(state);
    }

    public BPGame(BPApplication bp) {
        _program = bp;
        _lastPlayer = BPSystemPlayer.instance;
    }

    public void setLastPlayer(BPPlayer lastPlayer) {
        _lastPlayer = lastPlayer;
    }

    @Override
    public BPState getInitialState() {
        BPState state = new BPState(_program);
//        return applySimStart(state);
        return state;
    }

    @Override
    public BPPlayer[] getPlayers() {
        return _players;
    }

    @Override
    public BPPlayer getPlayer(BPState bpState) {
        if (_lastPlayer == EnvironmentPlayer.instance) {
            _lastPlayer = BPSystemPlayer.instance;
            return BPSystemPlayer.instance;
        } else {
            _lastPlayer = EnvironmentPlayer.instance;
            return EnvironmentPlayer.instance;
        }
    }

    @Override
    public List<BPAction> getActions(BPState state) {
//        bplog("generating action list");
        state.restore();
        List<BPAction> ans = new ArrayList<>();
        for (BTState bts : state.getBTstates()) {
//            bplog("inspecting " + bts.bt);
            for (RequestableInterface req : bts.requestedEvents) {
//                bplog("req=" + req);
                for (BEvent e : req.getEventList()) {
                    if (!state.getProgram().isBlocked(e)) {
                        BPAction act = new BPAction(e);
                        ans.add(act);
//                        bplog("added " + act);
                    }
                }
            }
        }

        bplog("actions possible: " + ans);
        return ans;
    }

    @Override
    public BPState getResult(BPState state, BPAction action) {
        return action.apply(state);
    }

    protected void bplog(String s) {
        if (debugMode)
            System.out.println("[" + this + "]: " + s);
    }

}
