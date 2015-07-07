package bpbwapi;

import bp.BEvent;
import bp.eventSets.EventsOfClass;
import bp.search.BPSearchApplication;
import bpbwapi.events.input.OnStartEvent;
import bpbwapi.events.input.UnitCreateEvent;
import bpbwapi.events.input.onFrameEvent;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;
import bwta.BWTA;

import java.io.InputStream;

/**
 * Created by orelmosheweinstock on 6/30/15.
 */
public class SCBWJavascriptApplication extends BPSearchApplication {

    protected Mirror _mirror = new Mirror();
    protected BPBWEventListener _listener;

    public SCBWJavascriptApplication() {
        super();
        _listener = new BPBWEventListener();
        _mirror.getModule().setEventListener(_listener);
    }

    @Override
    public void start() {
        super.start();
        _mirror.startGame();
    }

    @Override
    protected void setupGlobalScope() {
        super.setupGlobalScope();
        putInGlobalScope("unitCreateEvent",
                new EventsOfClass(UnitCreateEvent.class));
        putInGlobalScope("onFrameEvent",
                new EventsOfClass(onFrameEvent.class));
//        putInGlobalScope("onStart", new OnStartEvent());
        InputStream script = SCBWJavascriptApplication.class.getResourceAsStream("globalScopeInit.js");
        evaluateInGlobalScope(script, "SCBW-js-init");
    }
}
