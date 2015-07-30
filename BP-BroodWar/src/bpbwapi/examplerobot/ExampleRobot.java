package bpbwapi.examplerobot;

import bp.actuation.ActuatorFactory;
import bpbwapi.BPBWRobot;

/**
 * Created by moshewe on 27/07/2015.
 */
public class ExampleRobot extends BPBWRobot {

    public ExampleRobot(ExampleRobotApp app) {
        super(app);
        setBWListener(new ExampleListener(app, this));
    }

    public static void main(String[] args) throws InterruptedException {
        ExampleRobotApp app = new ExampleRobotApp();
        final ExampleRobot ex = new ExampleRobot(app);
        ex.start();
    }

    /**
     * a mechanism that takes events automaticly from output queue
     * operates the relevant actuator (the one that executes the action in the real world)
     * by event Type
     */
    @Override
    public void setupActuationService() {
        super.setupActuationService();
        Object[] paramList = {_game};
        ActuatorFactory<ListUnitsActuator> factory =
                new ActuatorFactory<>(ListUnitsActuator.class,
                        paramList);
        _actService.put(ListUnitsEvent.class, factory);// event type - "list units"
    }
}
