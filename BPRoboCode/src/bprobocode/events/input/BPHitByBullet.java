package bprobocode.events.input;

import robocode.HitByBulletEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPHitByBullet extends BPRobocodeEvent {
    public BPHitByBullet(HitByBulletEvent event) {
        super(event);
    }
}