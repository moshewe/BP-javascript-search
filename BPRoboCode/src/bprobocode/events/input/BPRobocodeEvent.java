package bprobocode.events.input;

import bp.BEvent;
import robocode.Event;

/**
 * Created by orelmosheweinstock on 6/12/15.
 */
public class BPRobocodeEvent extends BEvent {

    protected Event _event;

    public BPRobocodeEvent(Event event) {
        _event = event;
    }

    public Event getRobocodeEvent() {
        return _event;
    }
}
