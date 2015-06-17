package bprobocode.events.output;

import bprobocode.events.QuantifiedEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class Ahead extends QuantifiedEvent {

    public Ahead(int pixels) {
        super(pixels, true);
        _name = "Ahead";
    }
}
