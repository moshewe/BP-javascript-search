package bprobocode.events.input;

import robocode.ScannedRobotEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPScannedRobot extends BPRobocodeEvent {

    public BPScannedRobot(ScannedRobotEvent event) {
        super(event);
    }

    @Override
    public ScannedRobotEvent getRobocodeEvent() {
        return (ScannedRobotEvent) super.getRobocodeEvent();
    }
}
