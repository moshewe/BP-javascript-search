package bp.actuation;

import bp.BEvent;
import bp.BProgramControls;

import java.util.concurrent.Callable;

/**
 * Created by moshewe on 27/07/2015.
 */
public abstract class BActuator<EventType extends BEvent,
        ReturnType>  {

    public abstract ReturnType actuate(EventType event);

    protected void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + ": " + s);
    }

}
