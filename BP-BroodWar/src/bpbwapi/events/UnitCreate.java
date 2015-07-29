package bpbwapi.events;

import bp.events.WrapperEvent;
import bwapi.Unit;

/**
 * Created by orelmosheweinstock on 6/30/15.
 */
public class UnitCreate extends UnitEvent {

    public UnitCreate(Unit event) {
        super(event);
    }

}
