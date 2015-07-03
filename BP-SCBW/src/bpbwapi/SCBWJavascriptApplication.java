package bpbwapi;

import bp.eventSets.EventsOfClass;
import bp.search.BPSearchApplication;
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
    protected Game _game;
    protected Player _self;
    protected BPBWEventListener _listener;

    @Override
    protected void start() {
        super.start();
        _mirror.startGame();
        _game = _mirror.getGame();
        _self = _game.self();
        _mirror.getModule().setEventListener(_listener);

        //Use BWTA to analyze map
        //This may take a few minutes if the map is processed first time!
        bplog("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        bplog("Map data ready");
    }

    @Override
    protected void setupGlobalScope() {
        super.setupGlobalScope();
        putInGlobalScope("unitCreateEvent",
                new EventsOfClass(UnitCreateEvent.class));
        putInGlobalScope("onFrameEvent",
                new EventsOfClass(onFrameEvent.class));
        InputStream script = SCBWJavascriptApplication.class.getResourceAsStream("globalScopeInit.js");
        evaluateInGlobalScope(script, "SCBW-js-init");
    }
}
