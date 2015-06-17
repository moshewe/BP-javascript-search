package bprobocode;

/**
 * Created by orelmosheweinstock on 6/12/15.
 */

import bp.BEventVisitor;
import bprobocode.events.output.*;

/**
 * Visitor interface to handle ALL events from the BP system
 * to the robot. Must include a visit method for all such events.
 */
public class RobocodeEventVisitor extends BEventVisitor {

    protected BPRobot _rob;

    public RobocodeEventVisitor(BPRobot _rob) {
        this._rob = _rob;
    }

    public void visit(Ahead a) {
        _rob.ahead(a.getValue());
    }

    public void visit(Back b) {
        _rob.back(b.getValue());
    }

    public void visit(TurnGunLeft l) {
        _rob.turnGunLeft(l.getValue());
    }

    public void visit(TurnGunRight r) {
        _rob.turnGunRight(r.getValue());
    }

    public void visit(Fire f) {
        _rob.fire(f.getValue());
    }
}
