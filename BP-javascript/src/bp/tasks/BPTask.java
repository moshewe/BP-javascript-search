package bp.tasks;

import java.util.concurrent.Callable;

import static bp.BProgramControls.debugMode;

/**
 * Created by moshewe on 05/07/2015.
 */
public abstract class BPTask implements Runnable, Callable<Void>{

    @Override
    public Void call() throws Exception {
        run();
        return null;
    }

    public void bplog(String string) {
        if (debugMode)
            System.out.println("[" + this + "]: " + string);
    }

}
