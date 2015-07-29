package bpbwapi.events;

import bp.BEvent;
import bp.events.WrapperEvent;
import bwapi.Unit;

/**
 * Created by moshewe on 28/07/2015.
 */
public class UnitEvent extends WrapperEvent<Unit> {

    public UnitEvent(Unit event) {
        super(event);
    }
}
