package bpbwapi.examplebot;

import bp.BEvent;

/**
 * When this event is read from the output queue, list all units on screen.
 *
 * Created by orelmosheweinstock on 7/1/15.
 */
public class ListUnitsEvent extends BEvent {

    public ListUnitsEvent() {
        _outputEvent = true;
    }

}
