package bpbwapi;

import bp.actuation.ActuatorService;
import bp.tasks.ActuationTask;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    protected ExecutorService _executor = Executors.newCachedThreadPool();
    protected Runnable _startTask;

    public BPBWRobot(BWJavascriptApplication app) {
        _app = app;
        _listener = new BPBWEventListener(this, _app);
        _mirror.getModule().setEventListener(_listener);
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
