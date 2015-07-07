package bpbwapi.events.input;

import bp.BEvent;

/**
 * Created by moshewe on 07/07/2015.
 */
public class OnStartEvent extends BEvent{

    public OnStartEvent() {
        _name = "onStart";
        _outputEvent = true;
    }
}
