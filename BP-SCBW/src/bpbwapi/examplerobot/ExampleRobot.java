package bpbwapi.examplerobot;

import bp.Arbiter;
import bpbwapi.BPBWEventListener;
import bpbwapi.SCBWJavascriptApplication;

/**
 * Created by moshewe on 01/07/2015.
 */
public class ExampleRobot extends SCBWJavascriptApplication {

    public ExampleRobot() {
        super();
        _bp.setName("ExampleRobot");
        evaluateInGlobalScope("example-robots.js","examplebot");
        setupBThreadScopes();
        _bp.setArbiter(new Arbiter());
        _listener = new BPBWEventListener();
    }

    public static void main(String[] args) {
        System.out.println("Started ExampleRobot");
        ExampleRobot ex = new ExampleRobot();
        ex.start();
    }
}
