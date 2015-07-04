package bp.tasks;

import bp.BEvent;
import bp.BEventVisitor;
import bp.BJavascriptProgram;
import bp.BProgram;

/**
 * Created by moshewe on 03/07/2015.
 */
public class ActuatorLooper implements Runnable {
    private final BProgram _program;
    private BEventVisitor _vis;

    public ActuatorLooper(BProgram program,
                          BEventVisitor vis) {
        this._vis = vis;
        this._program = program;
    }

    @Override
    public void run() {
        BEvent outputEvent = _program.getOutputEvent();
        while (true) {
            outputEvent.accept(_vis);
            outputEvent = _program.getOutputEvent();
        }
    }
}
