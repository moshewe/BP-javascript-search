package bprobocode.events.input.robocodeEvents;

import robocode.BulletMissedEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPBulletMissed extends BPRobocodeEvent {

    public BPBulletMissed(BulletMissedEvent event) {
        super(event);
    }

    @Override
    public BulletMissedEvent getWrappedEvent() {
        return (BulletMissedEvent) super.getWrappedEvent();
    }
}
