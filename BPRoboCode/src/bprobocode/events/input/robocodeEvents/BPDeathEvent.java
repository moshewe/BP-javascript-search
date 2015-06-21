package bprobocode.events.input.robocodeEvents;

import robocode.DeathEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPDeathEvent extends BPRobocodeEvent {
    public BPDeathEvent(DeathEvent event) {
        super(event);
    }

    @Override
    public DeathEvent getWrappedEvent() {
        return (DeathEvent) super.getWrappedEvent();
    }
}
