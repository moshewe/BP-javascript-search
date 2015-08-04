package bpbwapi.examplerobot;

import bpbwapi.BWJavascriptApplication;

/**
 * Created by moshewe on 01/07/2015.
 */
public class ExampleBotApp extends BWJavascriptApplication {

    public ExampleBotApp() {
        super();
        _name = "ExampleBotApp";
        evaluateInGlobalScope("example-bot.js", "examplebot");
        setupBThreadScopes();
    }

}
