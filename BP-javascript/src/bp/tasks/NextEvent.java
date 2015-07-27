package bp.tasks;

import bp.Arbiter;
import bp.BEvent;
import bp.BPApplication;

/**
 * Created by moshewe on 04/07/2015.
 */
public class NextEvent extends BPTask {

    protected BPApplication _bp;
    protected Arbiter _arbiter;

    public NextEvent(BPApplication bp, Arbiter arbiter) {
        this._bp = bp;
        this._arbiter = arbiter;
    }

    @Override
    public void run() {
        if (_bp.getBThreads().isEmpty()) {
            bplog("=== ALL DONE!!! ===");
            return;
        }

        BEvent next = _arbiter.nextEvent();
        if (next == null) {
            bplog("no event chosen, waiting for an external event to be fired...");
            next = _bp.getInputEvent();
        }

        if (next.isOutputEvent()) {
            bplog(next + " is an output event.");
            _bp.emit(next);
        }

        _bp.triggerEvent(next);
        _bp.bthreadCleanup();
        run();
    }
}
