package bpbwapi;

import bp.BProgramControls;
import bpbwapi.events.input.OnFrameEvent;
import bpbwapi.events.input.OnStartEvent;
import bpbwapi.events.input.UnitCreateEvent;
import bwapi.DefaultBWListener;
import bwapi.Unit;
import bwta.BWTA;

/**
 * Created by moshewe on 30/06/2015.
 */
public class BPBWEventListener extends DefaultBWListener {

    private final BPBWRobot _robot;
    protected BWJavascriptApplication _app;

    public BPBWEventListener(BPBWRobot robot, BWJavascriptApplication app) {
        this._app = app;
        _robot = robot;
    }

    @Override
    public void onStart() {
        //Use BWTA to analyze map
        bplog("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        bplog("Map data ready");
        _robot.setGameFromMirror();
        _robot.setPlayerFromGame();
        _robot.setupActuationService();
        _robot.startActuation();
        _app.fire(new OnStartEvent());
    }

    @Override
    public void onFrame() {
        _app.fire(new OnFrameEvent());
    }

    @Override
    public void onUnitCreate(Unit unit) {
        _app.fire(new UnitCreateEvent(unit));
    }

    private void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + ": " + s);
    }

}
