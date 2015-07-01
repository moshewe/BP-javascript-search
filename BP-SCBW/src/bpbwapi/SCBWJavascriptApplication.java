package bpbwapi;

import bp.search.BPSearchApplication;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;

/**
 * Created by orelmosheweinstock on 6/30/15.
 */
public class SCBWJavascriptApplication extends BPSearchApplication {

    protected Mirror mirror = new Mirror();
    protected Game game;
    protected Player self;
    protected BPBWEventListener _listener;

    public SCBWJavascriptApplication() {
        game = mirror.getGame();
        self = game.self();
    }

    @Override
    protected void start() {
        super.start();
        mirror.getModule().setEventListener(_listener);
        mirror.startGame();
    }
}
