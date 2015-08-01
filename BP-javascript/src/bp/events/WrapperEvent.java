package bp.events;

import bp.BEvent;

/**
 * Created by orelmosheweinstock on 6/19/15.
 */
public abstract class WrapperEvent<T> extends BEvent {

    protected T _object;

    public WrapperEvent(T obj) {
        _object = obj;
    }

    public T getWrappedObject() {
        return _object;
    }
}
