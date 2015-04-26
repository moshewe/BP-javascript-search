package bp.search.events;

import bp.BEvent;

/**
 * Created by orelmosheweinstock on 4/9/15.
 */
public class SimStartEvent extends BEvent {

    private static SimStartEvent _instance;

    static {
        _instance = new SimStartEvent();
    }

    private SimStartEvent() {
        _name = "SimStart";
    }

    public static SimStartEvent getInstance() {
        return _instance;
    }

//    public SimStartEvent(String _name) {
//        this();
//    }
}
