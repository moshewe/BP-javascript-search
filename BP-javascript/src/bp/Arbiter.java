package bp;

import bp.exceptions.BPJRequestableSetException;

import java.util.Iterator;

/**
 * Default arbiter - triggers events according to the RWB semantics but promises
 * nothing as to the order of events triggered.
 *
 * @author moshewe
 */
public class Arbiter {

    /**
     * A counter that counts how many of the be-thread in allBThreads are busy.
     * B-Threads decrement this counter when they get into bSync just before
     * they become dormant and wait to be awaken. When the counter gets to zero,
     * the be-thread that decremented it from one to zero, awakes other
     * be-threads and sets the counter to the number of be-threads that it
     * awakes (the number of be-threads that are waiting to the next event)
     */
    transient volatile int busyBThreads = 0;

    private BProgram _program;

    private final Object synchLock = new Object() {
    };

    public BProgram getProgram() {
        return _program;
    }

    public void setProgram(BProgram program) {
        if (this._program != program) {
            this._program = program;
            for (BThread bt : program.getBThreads()) {
                bthreadAdded(bt);
            }
        }
    }

    public void bthreadAdded(BThread... bts) {
        busyBThreads += bts.length;
    }

    public static final BThread theUser = new BThread("The User") {
    };

    protected void bplog(String s) {
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
        Iterator<BEvent> it = _program.legalEvents().iterator();
        if (it.hasNext()) {
            return it.next();
        } else {
            return null;
        }
    }
}