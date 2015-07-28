package bpbwapi.events;

import bp.BEvent;
import bwapi.Unit;

/**
 * Created by moshewe on 28/07/2015.
 */
public class UnitDiscover extends UnitEvent {
    private final Unit _unit;

    public UnitDiscover(Unit unit) {
        _unit = unit;
    }
}
