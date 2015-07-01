package bpbwapi;

import bpbwapi.events.input.UnitCreateEvent;
import bpbwapi.events.input.onFrameEvent;
import bwapi.DefaultBWListener;
import bwapi.Unit;
import bwta.BWTA;

/**
 * Created by moshewe on 30/06/2015.
 */
public abstract class BPBWEventListener extends DefaultBWListener {

    protected SCBWJavascriptApplication _app;

    @Override
    public void onUnitCreate(Unit unit) {
        _app.fire(new UnitCreateEvent(unit));
    }

    @Override
    public void onStart() {
        //Use BWTA to analyze map
        //This may take a few minutes if the map is processed first time!
        System.out.println("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        System.out.println("Map data ready");
    }

    @Override
    public void onFrame() {
        _app.fire(new onFrameEvent());
    }

}
