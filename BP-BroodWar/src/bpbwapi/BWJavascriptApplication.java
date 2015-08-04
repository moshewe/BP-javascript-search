package bpbwapi;

import bp.eventSets.EventsOfClass;
import bp.search.BPSearchApplication;
import bpbwapi.events.UnitCreate;
import bpbwapi.events.FrameEvent;

import java.io.InputStream;

/**
 * Created by orelmosheweinstock on 6/30/15.
 */
public class BWJavascriptApplication extends BPSearchApplication {

    public BWJavascriptApplication() {
        super();
    }

    @Override
    public void start() throws InterruptedException {
        InputStream script = BWJavascriptApplication.class.getResourceAsStream("bw-sim-bthreads.js");
        evaluateInGlobalScope(script, "bw-sim-bthreads");
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
