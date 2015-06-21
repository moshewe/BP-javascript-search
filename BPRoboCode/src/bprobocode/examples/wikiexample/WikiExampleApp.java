package bprobocode.examples.wikiexample;

import bp.BPJavascriptApplication;
import bprobocode.BPRobocodeApplication;

import java.io.InputStream;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class WikiExampleApp extends BPRobocodeApplication {

    public WikiExampleApp() {
        setupBThreadScopes();
    }

    @Override
    protected void setupGlobalScope() {
        super.setupGlobalScope();
        InputStream script =
                BPJavascriptApplication.class.getResourceAsStream("globalScopeInit.js");
        evaluateInGlobalScope(script, "WikiExmpleInit");
    }
}
