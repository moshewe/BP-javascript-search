package bprobocode.events.output;

import bprobocode.events.QuantifiedEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class TurnGunRight extends QuantifiedEvent {

    public TurnGunRight(int pixels) {
        super(pixels, true);
        _name = "TurnGunRight";
    }
}
