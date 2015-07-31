package bpbwapi.optimalMineralsExample;

import bpbwapi.BWJavascriptApplication;

/**
 * Created by orelmosheweinstock on 7/30/15.
 */
public class OptimalMineralsApp extends BWJavascriptApplication {
    public OptimalMineralsApp() {
        super();
        _name = "OptimalMinerals";
        evaluateInGlobalScope("optimal-minerals.js", "minerals-bot");
        setupBThreadScopes();
    }
}
