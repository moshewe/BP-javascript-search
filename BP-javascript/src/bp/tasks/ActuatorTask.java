package bp.tasks;

import bp.BEvent;
import bp.BProgram;

/**
 * Created by moshewe on 03/07/2015.
 */
public class ActuatorTask extends BPTask {
    private final BProgram _program;

    public ActuatorTask(BProgram program) {
        this._program = program;
    }

    @Override
    public void run() {
        while (true) {
            BEvent outputEvent = _program.dequeueOutputEvent();
            outputEvent.accept(_program.getActuator());
        }
    }
}