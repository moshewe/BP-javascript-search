package bp.tasks;

import bp.BEvent;
import bp.BPApplication;
import bp.actuation.ActuatorService;

import java.util.concurrent.Callable;

/**
 * Created by moshewe on 27/07/2015.
 */
public class ActuationTask implements Callable<Void>, Runnable {

    protected BPApplication _app;
    protected ActuatorService _actService;

    public ActuationTask(BPApplication _app,
                         ActuatorService actuationService) {
        this._app = _app;
        _actService = actuationService;
    }

    @Override
    public Void call() throws Exception {
        System.out.println("ActuationTask started!");
        while (true) {
            BEvent event = _app.dequeueOutputEvent();
            System.out.println(event + " read from output queue");
            _actService.actuate(event);
        }
    }

    @Override
    public void run() {
        try {
            call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
