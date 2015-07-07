package bpbwapi;

import bp.BProgramControls;
import bpbwapi.events.input.OnStartEvent;
import bpbwapi.events.input.UnitCreateEvent;
import bpbwapi.events.input.onFrameEvent;
import bwapi.DefaultBWListener;
import bwapi.Unit;
import bwta.BWTA;

/**
 * Created by moshewe on 30/06/2015.
 */
public class BPBWEventListener extends DefaultBWListener {

    protected SCBWJavascriptApplication _app;

    @Override
    public void onStart() {
        bplog("onStart event fired!");
        _app.fire(new OnStartEvent());
    }

    @Override
    public void onFrame() {
        bplog("onFrame event fired!");
        _app.fire(new onFrameEvent());
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
