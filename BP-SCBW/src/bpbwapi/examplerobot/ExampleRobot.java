package bpbwapi.examplerobot;

import bp.actuation.ActuatorFactory;
import bpbwapi.BPBWRobot;
import bpbwapi.BWJavascriptApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by moshewe on 27/07/2015.
 */
public class ExampleRobot extends BPBWRobot {

    public ExampleRobot(BWJavascriptApplication app) {
        super(app);
    }

    @Override
    public void setupActuationService() {
        super.setupActuationService();
        Object[] paramList = {_game};
        ActuatorFactory<ListUnitsActuator> factory =
                new ActuatorFactory<>(ListUnitsActuator.class,
                        paramList);
        _actService.put(ListUnitsEvent.class, factory);
    }

    public static void main(String[] args) throws InterruptedException {
        ExampleRobotApp app = new ExampleRobotApp();
        final ExampleRobot ex = new ExampleRobot(app);
        ex.start();
    }
}
