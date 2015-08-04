package bpbwapi.examplebot;

import bpbwapi.BPBroodWarBot;
import bpbwapi.BWJavascriptApplication;
import bpbwapi.events.BPDefaultBWListener;
import bpbwapi.events.UnitCreate;
import bwapi.Unit;

/**
 * Created by moshewe on 29/07/2015.
 */
public class ExampleListener extends BPDefaultBWListener {

    public ExampleListener(BWJavascriptApplication app, BPBroodWarBot robot) {
        super(app, robot);
    }

    @Override
    public void onUnitCreate(Unit unit) {
        _app.fire(new UnitCreate(unit));
    }
}
