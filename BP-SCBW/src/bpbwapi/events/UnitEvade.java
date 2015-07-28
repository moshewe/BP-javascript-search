package bpbwapi.events;

import bp.BEvent;
import bwapi.Unit;

/**
 * Created by moshewe on 28/07/2015.
 */
public class UnitEvade extends UnitEvent {
    public UnitEvade(Unit unit) {
        _unit = unit;
    }
}
