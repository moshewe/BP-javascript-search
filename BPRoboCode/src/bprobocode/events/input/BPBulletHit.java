package bprobocode.events.input;

import robocode.BulletHitEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPBulletHit extends BPRobocodeEvent {
    public BPBulletHit(BulletHitEvent event) {
        super(event);
    }

    @Override
    public BulletHitEvent getRobocodeEvent() {
        return (BulletHitEvent) super.getRobocodeEvent();
    }
}
