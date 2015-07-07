package bpbwapi.examplerobot;

import bp.tasks.ActuatorTask;
import bp.Arbiter;
import bpbwapi.BPBWEventListener;
import bpbwapi.SCBWJavascriptApplication;

/**
 * Created by moshewe on 01/07/2015.
 */
public class ExampleRobot extends SCBWJavascriptApplication {

    public ExampleRobot() {
        super();
        _name = "ExampleRobot";
        evaluateInGlobalScope("example-robots.js", "examplebot");
        setupBThreadScopes();
        _actuator = new ExampleRobotActuator(_mirror);
    }

    public static void main(String[] args) {
        final ExampleRobot ex = new ExampleRobot();
        ex.start();
    }
}
