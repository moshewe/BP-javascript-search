package bp.tasks;

import bp.BEvent;

/**
 * Created by moshewe on 04/07/2015.
 */
public class BThreadManager implements Runnable {

    @Override
    public void run() {
        if (_bthreads.isEmpty()) {
            bplog("=== ALL DONE!!! ===");
            return;
        }

        BEvent next = _arbiter.nextEvent();
        if (next == null) {
            bplog("no event chosen, waiting for an external event to be fired...");
            next = dequeueInputEvent();
        } else if (next.isOutputEvent()) {
            bplog(next + " is an output event.");
            publishEvent(next);
        }

        triggerEvent(next);
        bthreadCleanup();
        loop();
    }
}
