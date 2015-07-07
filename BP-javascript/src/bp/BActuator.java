package bp;

import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public abstract class BActuator {

    public BActuator() {
    }

    public void visit(BEvent event) {
        bplog("Unimplemented");
    }

    protected void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + ": " + s);
    }
}
