package bpbwapi.events;

import bp.BEvent;
import bwapi.Position;

/**
 * Created by moshewe on 28/07/2015.
 */
public class NukeDetect extends BEvent {
    private final Position _position;

    public NukeDetect(Position position) {
        _position = position;
    }
}
