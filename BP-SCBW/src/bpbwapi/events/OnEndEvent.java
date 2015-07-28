package bpbwapi.events;

import bp.BEvent;

/**
 * Created by moshewe on 28/07/2015.
 */
public class OnEndEvent extends BEvent {
    private final boolean _end;

    public OnEndEvent(boolean end) {
        _end = end;
    }
}
