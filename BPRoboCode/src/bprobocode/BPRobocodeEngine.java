package bprobocode;

import robocode.control.RobocodeEngine;

/**
 * Created by orelmosheweinstock on 6/19/15.
 */
public class BPRobocodeEngine extends RobocodeEngine {

    private final BPRobocodeApplication _app;
    BPBattleAdaptor _adaptor;

    public BPRobocodeEngine(BPRobocodeApplication app) {
        _app = app;
        _adaptor = new BPBattleAdaptor(_app);
        addBattleListener(_adaptor);
    }
}
