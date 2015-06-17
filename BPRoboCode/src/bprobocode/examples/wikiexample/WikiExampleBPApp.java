package bprobocode.examples.wikiexample;

import bprobocode.BPRobocodeApplication;

import java.io.InputStream;

/**
 * Created by orelmosheweinstock on 6/15/15.
 */
public class WikiExampleBPApp extends BPRobocodeApplication {

    public WikiExampleBPApp() {
        super();
        _bp.setName("WikiExampleRobot");
        InputStream script = getClass().getResourceAsStream("script.js");
        evaluateInGlobalScope(script, "WikiExampleRobotScript");
        setupBThreadScopes();
    }
}
