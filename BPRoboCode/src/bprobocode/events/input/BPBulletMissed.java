package bprobocode.events.input;

import robocode.BulletMissedEvent;
import robocode.Event;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPBulletMissed extends BPRobocodeEvent {

    public BPBulletMissed(Event event) {
        super(event);
    }

    @Override
    public BulletMissedEvent getRobocodeEvent() {
        return (BulletMissedEvent) super.getRobocodeEvent();
    }
}
