package bprobocode.events.input;

import robocode.DeathEvent;
import robocode.Event;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPDeathEvent extends BPRobocodeEvent {
    public BPDeathEvent(Event event) {
        super(event);
    }

    @Override
    public DeathEvent getRobocodeEvent() {
        return (DeathEvent) super.getRobocodeEvent();
    }
}
