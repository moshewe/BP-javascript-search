package bpbwapi;

import bp.actuation.ActuatorService;
import bp.tasks.ActuationTask;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;

/**
 * Created by moshewe on 27/07/2015.
 */
public class BPBWRobot {

    protected BWJavascriptApplication _app;
    protected Mirror _mirror = new Mirror();
    protected BPBWEventListener _listener;
    protected Game _game;
    protected Player _self;
    protected ActuatorService _actService;

    public BPBWRobot(BWJavascriptApplication app) {
        _app = app;
        _listener = new BPBWEventListener(this,_app);
        _mirror.getModule().setEventListener(_listener);
    }

    public void start() throws InterruptedException {
        _app.start();
        _mirror.startGame();
        setupActuationService();
    }

    public void setupActuationService() {
        _actService = new ActuatorService();
    }

    public void setGameFromMirror() {
        _game = _mirror.getGame();
    }

    public void setPlayerFromGame() {
        _self = _game.self();
    }

    public ActuationTask generateActuationTask(){
        return new ActuationTask(_app,_actService);
    }
}
