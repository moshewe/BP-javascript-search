package bpbwapi.events.output;

import bpbwapi.events.UnitEvent;
import bwapi.Unit;

/**
 * Created by orelmosheweinstock on 8/1/15.
 */
public class GatherCommand extends UnitEvent {

    private final Unit _target;

    public GatherCommand(Unit event, Unit target) {
        super(event);
        _target = target;
        _outputEvent = true;
    }

    public Unit getTarget() {
        return _target;
    }
}
