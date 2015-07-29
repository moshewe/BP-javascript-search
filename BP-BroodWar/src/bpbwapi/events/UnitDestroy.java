package bpbwapi.events;

import bp.BEvent;
import bwapi.Unit;

/**
 * Created by moshewe on 28/07/2015.
 */
public class UnitDestroy extends UnitEvent {
    public UnitDestroy(Unit event) {
        super(event);
    }
}
