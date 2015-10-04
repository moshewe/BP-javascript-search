package bpbwapi;

import bp.search.BPSearchApplication;
import bp.search.adversarial.BPGame;
import bp.search.adversarial.BPIterativeDeepeningAlphaBeta;

import java.io.InputStream;

/**
 * Created by orelmosheweinstock on 6/30/15.
 */
public class BWJavascriptApplication extends BPSearchApplication {

    public BWJavascriptApplication() {
        super();
        BPGame bwgame = new BWGame(this);
        super._arbiter = new BPIterativeDeepeningAlphaBeta(bwgame, Double.MIN_VALUE,
                Double.MAX_VALUE, 100);
        InputStream script = BWJavascriptApplication.class.getResourceAsStream("bw-sim-bthreads.js");
        evaluateInGlobalScope(script, "bw-sim-bthreads");
    }

    @Override
    public void start() throws InterruptedException {
        super.start();
    }

    @Override
    protected void setupGlobalScope() {
        super.setupGlobalScope();
//        putInGlobalScope("unitCreateEvent",
//                new EventsOfClass(UnitCreate.class));
//        putInGlobalScope("onFrameEvent",
//                new EventsOfClass(FrameEvent.class));
        InputStream script = BWJavascriptApplication.class.getResourceAsStream("globalScopeInit.js");
        evaluateInGlobalScope(script, "bw-js-init");
    }
}
