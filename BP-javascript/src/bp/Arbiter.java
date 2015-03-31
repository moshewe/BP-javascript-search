package bp;

import bp.exceptions.BPJRequestableSetException;

import java.util.Iterator;
import java.util.Set;

import static bp.BProgramControls.debugMode;

/**
 * Default arbiter - triggers events according to the RWB semantics but promises
 * nothing as to the order of events triggered.
 *
 * @author moshewe
 */
public class Arbiter {

    private BProgram _program;

    public BProgram getProgram() {
        return _program;
    }

    public void setProgram(BProgram program) {
        this._program = program;
    }

    protected void bplog(String s) {
        if (debugMode)
            System.out.println("[" + getProgram() + ":" + this + "]: " + s);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Choose the next event to be fired. Notifies the _program which thread
     * asked for it.
     *
     * @throws BPJRequestableSetException
     */
    protected BEvent nextEvent() {
        BEvent ec = selectEventFromProgram();
        bplog("Event chosen from program is " + ec);
        // if no internal event was selected, wait for an external event
        return ec;

    }

    protected BEvent selectEventFromProgram() {
        Set<BEvent> legals = _program.legalEvents();
        Iterator<BEvent> it = legals.iterator();
        if (it.hasNext()) {
            return it.next();
        } else {
            return null;
        }
    }
}