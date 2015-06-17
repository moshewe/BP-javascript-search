package bprobocode.events.output;

import bprobocode.events.QuantifiedEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class TurnGunLeft extends QuantifiedEvent {
    public TurnGunLeft(int pixels) {
        super(pixels, true);
        _name = "TurnGunLeft";
    }
}
