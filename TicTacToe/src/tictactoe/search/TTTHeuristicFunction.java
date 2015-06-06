package tictactoe.search;

import aima.core.search.framework.HeuristicFunction;
import bp.BEvent;
import bp.search.BPState;

import static tictactoe.events.StaticEvents.*;

import static bp.BProgramControls.debugMode;

/**
 * Created by orelmosheweinstock on 6/6/15.
 */
public class TTTHeuristicFunction implements HeuristicFunction {

    public double h(BPState state) {
        double retVal;
        BEvent lastEvent = state._program.getLastEvent();
        if (lastEvent == OWin) {
            retVal = 1;
        } else if (lastEvent == XWin) {
            retVal = -1;
        } else {
            retVal = 0;
        }

        bplog("utility is " + retVal);
        return retVal;
    }

    @Override
    public double h(Object state) {
        return h((BPState) state);
    }

    public void bplog(String s) {
        if (debugMode)
            System.out.println("[" + this.getClass().getSimpleName() + "]: "
                    + s);
    }

}
