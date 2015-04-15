package bp.search.events;

import bp.BEvent;

/**
 * Created by orelmosheweinstock on 4/9/15.
 */
public class SimStartEvent extends BEvent {

    public SimStartEvent() {
        _name = "SimStart";
    }

    public SimStartEvent(String _name) {
        this();
    }
}
