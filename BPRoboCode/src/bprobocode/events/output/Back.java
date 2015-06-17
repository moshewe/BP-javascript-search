package bprobocode.events.output;

import bprobocode.events.QuantifiedEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class Back extends QuantifiedEvent {

    public Back(int pixels) {
        super(pixels, true);
        _name = "Back";
    }
}
