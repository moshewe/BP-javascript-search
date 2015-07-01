package bpbwapi.events.input;

import bp.events.WrapperEvent;
import bwapi.Unit;

/**
 * Created by orelmosheweinstock on 6/30/15.
 */
public class UnitCreateEvent extends WrapperEvent<Unit> {

    public UnitCreateEvent(Unit event) {
        super(event);
    }

}
