package bpbwapi.examplerobot;

import bp.actuation.ActuatorFactory;
import bp.actuation.ActuatorService;
import bp.tasks.ActuationTask;
import bpbwapi.BPBWRobot;
import bpbwapi.BWJavascriptApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
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
        ExecutorService executor = Executors.newCachedThreadPool();
        // THINK HOW TO START THE GAME WITHOUT BLOCKING HERE...
        // EXECUTE THE GAME START METHOD??
        // SETUP ACTUATION SERVICES ONSTART???
        Callable<Void> starter = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    ex.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        List<Callable<Void>> startAppList = new ArrayList<>();
        startAppList.add(starter);
        executor.invokeAll(startAppList);
        executor.execute(ex.generateActuationTask());
    }
}
