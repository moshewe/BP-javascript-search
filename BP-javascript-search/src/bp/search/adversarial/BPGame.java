package bp.search.adversarial;

import aima.core.search.adversarial.Game;
import bp.BEvent;
import bp.eventSets.RequestableInterface;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.BTState;
import bp.search.adversarial.players.BPSystemPlayer;
import bp.search.adversarial.players.EnvironmentPlayer;
import bp.search.bthreads.TerminalStateDetector;
import bp.search.bthreads.UtilityEventListener;

import java.util.ArrayList;
import java.util.List;

import static bp.BProgramControls.debugMode;

public abstract class BPGame implements Game<BPState, BPAction, BPPlayer> {

    protected static BPPlayer[] players = {BPSystemPlayer.instance,
            EnvironmentPlayer.instance};

    @Override
    public BPPlayer[] getPlayers() {
        return players;
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
