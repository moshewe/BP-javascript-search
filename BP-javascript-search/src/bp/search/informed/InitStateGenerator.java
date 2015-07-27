package bp.search.informed;

import bp.BPApplication;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.events.SimStartEvent;

import static bp.BProgramControls.debugMode;

/**
 * Created by orelmosheweinstock on 6/6/15.
 */
public class InitStateGenerator {

    protected BPApplication _program;

    public InitStateGenerator(BPApplication program) {
        _program = program;
    }

    public BPState genInitState() {
        return applySimStart(genBPStateFromProgram());
    }

    protected BPState genBPStateFromProgram() {
        return new BPState(_program);
    }

    protected BPState applySimStart(BPState state) {
        BPAction simStartAction = new BPAction(SimStartEvent.getInstance());
        bplog("creating simulation initial state...");
        BPState retState = simStartAction.apply(state);
        bplog("FINISHED creating simulation initial state...");
        return retState;
    }

    public void bplog(String s) {
        if (debugMode)
            System.out.println("[" + getClass().getSimpleName() + "]: "
                    + s);
    }

}
