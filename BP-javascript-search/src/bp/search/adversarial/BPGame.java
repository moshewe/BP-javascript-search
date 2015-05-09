package bp.search.adversarial;

import aima.core.search.adversarial.Game;
import bp.BEvent;
import bp.BProgram;
import bp.BThread;
import bp.eventSets.RequestableInterface;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.BTState;
import bp.search.adversarial.players.BPSystemPlayer;
import bp.search.adversarial.players.EnvironmentPlayer;
import bp.search.events.SimStartEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static bp.BProgramControls.debugMode;

public abstract class BPGame implements Game<BPState, BPAction, BPPlayer> {

    protected static BPPlayer[] _players = {BPSystemPlayer.instance,
            EnvironmentPlayer.instance};
    protected Collection<BThread> _simBThreads = new ArrayList<>();
    protected BProgram _program;
    private BPPlayer _lastPlayer;

    public BPGame(BProgram bp) {
        _program = bp;
    }

    @Override
    public BPState getInitialState() {
        BPState state = new BPState(_program);
        BPAction simStartAction = new BPAction(SimStartEvent.getInstance());
        bplog("creating simulation initial state...");
        BPState retState = simStartAction.apply(state);
        bplog("FINISHED creating simulation initial state...");
        return retState;
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
