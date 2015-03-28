package bp.search;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import bp.BEvent;
import bp.BProgram;
import bp.BThread;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author moshewe
 * @deprecated
 */
public class BPActionsFunction implements ActionsFunction, Serializable {

    protected static BPAction sandboxOn =
            new BPAction(EnvironmentSimBThread.simOn);
    protected static BPAction sandboxOff =
            new BPAction(EnvironmentSimBThread.simOff);

    public Set<Action> actions(BPState state) throws ClassNotFoundException,
            IOException {
        // sandboxOn.applyInPlace(state);
        Set<Action> actions = new TreeSet<Action>();
        BProgram bp = state.bp;
        for (BThread bt : bp.getBThreads()) {
            bplog("examining events for " + bt);
            for (BEvent e : bt.getRequestedEvents().getEventList()) {
                if (!bp.isBlocked(e)) {
                    bplog("added " + e);
                    actions.add(new BPAction(e));
                }
            }
        }
        // sandboxOff.applyInPlace(state);
        bplog("actions possible: " + actions);
        return actions;
    }

    @Override
    public Set<Action> actions(Object s) {
        try {
            return actions((BPState) s);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void bplog(String s) {
        System.out.println("[" + this.getClass().getSimpleName() + "]: "
                + s);
    }
}
