package bp.search.adversarial;

import aima.core.search.adversarial.Game;
import bp.BEvent;
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

    @Override
    public BPState getInitialState() {
        BPState state = makeInitialState();
        BPAction simStartAction = new BPAction(SimStartEvent.getInstance());
        bplog("creating simulation initial state...");
        simStartAction.apply(state);
        bplog("FINISHED creating simulation initial state...");
        return state;
    }

    protected abstract BPState makeInitialState();

    @Override
    public BPPlayer[] getPlayers() {
        return _players;
    }

    @Override
    public List<BPAction> getActions(BPState state) {
        state.restore();
        List<BPAction> ans = new ArrayList<>();
        for (BTState bts : state.getBTstates()) {
            for (RequestableInterface req : bts.requestedEvents) {
                for (BEvent e : req.getEventList()) {
                    if (!state.getBp().isBlocked(e)) {
                        ans.add(new BPAction(e));
                    }
                }
            }
        }

        bplog("actions possible: " + ans);
        return ans;
    }

    protected void bplog(String s) {
        if (debugMode)
            System.out.println("[" + this + "]: " + s);
    }

}
