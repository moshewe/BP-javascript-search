package bprobocode.events;

import bp.BEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public abstract class QuantifiedEvent extends BEvent {

    protected int _pixels;

    public QuantifiedEvent(int pixels, boolean b) {
        this(pixels);
        _outputEvent = b;
    }

    public QuantifiedEvent(int pixels) {
        super();
        _pixels = pixels;
    }

    public int getValue() {
        return _pixels;
    }
}
