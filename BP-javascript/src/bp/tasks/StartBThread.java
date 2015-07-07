package bp.tasks;

import bp.BEvent;
import bp.BThread;

/**
 * Created by moshewe on 06/07/2015.
 */
public class StartBThread extends BPTask {
    private BThread _bt;

    public StartBThread(BThread _bt) {
        this._bt = _bt;
    }

    @Override
    public void run() {
        _bt.start();
    }
}
