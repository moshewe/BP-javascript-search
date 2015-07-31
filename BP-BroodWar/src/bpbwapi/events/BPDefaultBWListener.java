package bpbwapi.events;

import bp.BProgramControls;
import bpbwapi.BPBroodWarBot;
import bpbwapi.BWJavascriptApplication;
import bwapi.DefaultBWListener;
import bwta.BWTA;

/**
 * Created by moshewe on 29/07/2015.
 */
public class BPDefaultBWListener extends DefaultBWListener {
    protected final BPBroodWarBot _robot;
    protected BWJavascriptApplication _app;

    public BPDefaultBWListener(BWJavascriptApplication app, BPBroodWarBot robot) {
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
        _app.fire(new StartEvent());
    }

    @Override
    public void onFrame() {
        _app.fire(new FrameEvent());
    }

    private void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + ": " + s);
    }

    @Override
    public void onEnd(boolean b) {
        _app.fire(new OnEndEvent(b));
    }
}
