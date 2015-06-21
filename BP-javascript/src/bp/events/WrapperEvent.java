package bp.events;

import bp.BEvent;

/**
 * Created by orelmosheweinstock on 6/19/15.
 */
public abstract class WrapperEvent<E> extends BEvent {

    protected E _event;

    public WrapperEvent(E event) {
        _event = event;
    }

    public E getWrappedEvent() {
        return _event;
    }
}
