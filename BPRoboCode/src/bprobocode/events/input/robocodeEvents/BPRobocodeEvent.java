package bprobocode.events.input.robocodeEvents;

import bp.events.WrapperEvent;
import robocode.Event;

/**
 * Created by orelmosheweinstock on 6/12/15.
 */
public abstract class BPRobocodeEvent<RE extends Event> extends
        WrapperEvent<RE> {

    public BPRobocodeEvent(RE event) {
        super(event);
    }
}
