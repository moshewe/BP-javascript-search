package bpbwapi;

import bp.actuation.ActuatorService;
import bp.tasks.ActuationTask;
import bpbwapi.events.BPDefaultBWListener;
import bwapi.BWEventListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by moshewe on 27/07/2015.
 */
public class BPBroodWarBot {

    protected BWJavascriptApplication _app;
    protected Mirror _mirror = new Mirror();
    protected BWEventListener _listener;
    protected Game _game;
    protected Player _self;
    protected ActuatorService _actService;
    protected ExecutorService _executor = Executors.newCachedThreadPool();
    protected Runnable _startTask;

    public BPBroodWarBot(BWJavascriptApplication app) {
        _app = app;
        setBWListener(new BPDefaultBWListener(_app, this));
        _startTask = new Runnable() {
            @Override
            public void run() {
                try {
                    _app.start();
                    _mirror.startGame();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    protected void setBWListener(BPDefaultBWListener listener) {
        _listener = listener;
        _mirror.getModule().setEventListener(_listener);
    }

    public void start() throws InterruptedException {
        _executor.execute(_startTask);
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

    public ActuationTask generateActuationTask() {
        return new ActuationTask(_app, _actService);
    }

    public void startActuation(){
        _executor.execute(generateActuationTask());
    }
}
