package bpbwapi.events;

import bp.BEvent;
import bwapi.Unit;

/**
 * Created by moshewe on 28/07/2015.
 */
public class UnitShow extends BEvent {
    private final Unit _unit;

    public UnitShow(Unit unit) {
        _unit = unit;
    }
}
