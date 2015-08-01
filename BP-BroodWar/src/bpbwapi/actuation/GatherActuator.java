package bpbwapi.actuation;

import bpbwapi.BWActuator;
import bpbwapi.events.output.GatherCommand;
import bwapi.Game;
import bwapi.Unit;

/**
 * Created by orelmosheweinstock on 8/1/15.
 */
public class GatherActuator extends BWActuator<GatherCommand, Void> {
    public GatherActuator(Game game) {
        super(game);
    }

    @Override
    public Void actuate(GatherCommand event) {
        Unit unit = event.getWrappedObject();
        unit.gather(event.getTarget());
        return null;
    }
}
