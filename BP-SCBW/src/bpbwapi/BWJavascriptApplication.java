package bpbwapi;

import bp.eventSets.EventsOfClass;
import bp.search.BPSearchApplication;
import bpbwapi.events.input.UnitCreateEvent;
import bpbwapi.events.input.OnFrameEvent;
import bwapi.Mirror;

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
        super.start();
    }

    @Override
    protected void setupGlobalScope() {
        super.setupGlobalScope();
        putInGlobalScope("unitCreateEvent",
                new EventsOfClass(UnitCreateEvent.class));
        putInGlobalScope("onFrameEvent",
                new EventsOfClass(OnFrameEvent.class));
        InputStream script = BWJavascriptApplication.class.getResourceAsStream("globalScopeInit.js");
        evaluateInGlobalScope(script, "SCBW-js-init");
    }
}
