package bp;

import bp.exceptions.BPJRequestableSetException;

import java.util.Iterator;
import java.util.Set;

import static bp.BProgramControls.debugMode;

/**
 * Default _arbiter - triggers events according to the RWB semantics but promises
 * nothing as to the order of events triggered.
 *
 * @author moshewe
 */
public class Arbiter {

    protected BPApplication _app;

    public BPApplication getProgram() {
        return _app;
    }

    public void setProgram(BPApplication program) {
        this._app = program;
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
     * Choose the next event to be fired. Notifies the _app which thread
     * asked for it.
     *
     * @throws BPJRequestableSetException
     */
    public BEvent nextEvent() {
        BEvent ec = selectEventFromProgram();
        bplog("Event chosen from program is " + ec);
        // if no internal event was selected, wait for an external event
        return ec;

    }

    protected BEvent selectEventFromProgram() {
        Set<BEvent> legals = _app.legalEvents();
        Iterator<BEvent> it = legals.iterator();
        if (it.hasNext()) {
            return it.next();
        } else {
            return null;
        }
    }
}