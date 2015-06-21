package bprobocode.events.input.robocodeEvents;

import robocode.ScannedRobotEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPScannedRobot extends BPRobocodeEvent {

    public BPScannedRobot(ScannedRobotEvent event) {
        super(event);
    }

    @Override
    public ScannedRobotEvent getWrappedEvent() {
        return (ScannedRobotEvent) super.getWrappedEvent();
    }

}
