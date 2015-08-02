package bpbwapi.events.internal;

import bp.BEvent;
import bwapi.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moshewe on 02/08/2015.
 */
public class KnownMinerals extends BEvent {

    protected List<Unit> _minerals;

    public KnownMinerals(List<Unit> minerals) {
        this._minerals = minerals;
    }

    public List<Unit> getMinerals() {
        return _minerals;
    }

}
