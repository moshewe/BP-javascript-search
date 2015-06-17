package bprobocode.events.input;

import bp.BEvent;
import robocode.*;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPRobocodeEventFactory {

    public BPScannedRobot createFromRobocodeEvent(ScannedRobotEvent e) {
        return new BPScannedRobot(e);
    }

    public BPBattleEnded createFromRobocodeEvent(BattleEndedEvent event) {
        return new BPBattleEnded(event);
    }

    public BPBulletHit createFromRobocodeEvent(BulletHitEvent event) {
        return new BPBulletHit(event);
    }

    public BPBulletHitBullet createFromRobocodeEvent(BulletHitBulletEvent event) {
        return new BPBulletHitBullet(event);
    }

    public BPBulletMissed createFromRobocodeEvent(BulletMissedEvent event) {
        return new BPBulletMissed(event);
    }

    public BPDeathEvent createFromRobocodeEvent(DeathEvent event) {
        return new BPDeathEvent(event);
    }

    public BPHitByBullet createFromRobocodeEvent(HitByBulletEvent event) {
        return new BPHitByBullet(event);
    }

    public BEvent createFromRobocodeEvent(HitRobotEvent event) {
        return new BPHitRobot(event);
    }

    public BEvent createFromRobocodeEvent(HitWallEvent event) {
        return null;
    }

    public BEvent createFromRobocodeEvent(RobotDeathEvent event) {
        return null;
    }

    public BEvent createFromRobocodeEvent(WinEvent event) {
        return null;
    }

    public BEvent createFromRobocodeEvent(RoundEndedEvent event) {
        return new BPRoundEnded(event);
    }
}
