package bp.search.framework;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import bp.BEvent;
import bp.eventSets.RequestableInterface;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.BTState;

import java.util.Set;
import java.util.TreeSet;

import static bp.BProgramControls.debugMode;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public class BPActionsFunction implements ActionsFunction {

    public Set<Action> actions(BPState state) {
//        bplog("generating action list");
        state.restore();
        Set<Action> ans = new TreeSet<>();
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

    protected void bplog(String s) {
        if (debugMode)
            System.out.println("[" + this + "]: " + s);
    }

    @Override
    public Set<Action> actions(Object s) {
        return actions((BPState) s);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
