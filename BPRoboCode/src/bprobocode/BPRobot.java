package bprobocode;

import bprobocode.events.input.robocodeEvents.BPRobocodeEventFactory;
import robocode.*;

/**
 * Created by orelmosheweinstock on 6/11/15.
 */
public abstract class BPRobot extends Robot {

    protected BPRobocodeApplication _app;
    protected RobocodeEventVisitor _vis = new RobocodeEventVisitor(this);
    protected BPRobocodeEventFactory _fact = new BPRobocodeEventFactory();

    public BPRobot(BPRobocodeApplication _app) {
        super();
        this._app = _app;
    }

    @Override
    public void run() {
        while (true) {
            _app.actuatorLoop(_vis);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onBulletHit(BulletHitEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onBulletMissed(BulletMissedEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onDeath(DeathEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onWin(WinEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

    @Override
    public void onBattleEnded(BattleEndedEvent event) {
        _app.fire(_fact.createFromRobocodeEvent(event));
    }

}
