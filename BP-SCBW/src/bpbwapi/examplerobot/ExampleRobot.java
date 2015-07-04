package bpbwapi.examplerobot;

import bp.tasks.ActuatorLooper;
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
        evaluateInGlobalScope("example-robots.js", "examplebot");
        setupBThreadScopes();
        _bp.setArbiter(new Arbiter());
        _listener = new BPBWEventListener();
    }

    public static void main(String[] args) {
        final ExampleRobot ex = new ExampleRobot();
        ActuatorLooper looper = new ActuatorLooper(ex,
                new ExampleRobotVisitor());
        Thread appThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ex.start();
            }
        });
        Thread looperThread = new Thread(looper);
        appThread.start();
        looperThread.start();
        try {
            appThread.join();
            looperThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
