package bp.tasks;

import bp.BEvent;
import bp.BThread;

/**
 * Created by moshewe on 06/07/2015.
 */
public class ResumeBThread extends BPTask {
    private BThread _bt;
    private BEvent _event;

    public ResumeBThread(BThread _bt, BEvent _event) {
        this._bt = _bt;
        this._event = _event;
    }

    @Override
    public void run() {
        if(_bt.isWaited(_event)){
            bplog("resuming " + this);
            _bt.resume(_event);
            return;
        }

        if(_bt.isRequested(_event)){
            bplog("resuming " + this);
            _bt.resume(_event);
            return;
        }
    }

    @Override
    public String toString() {
        return "Resume" + _bt;
    }
}
