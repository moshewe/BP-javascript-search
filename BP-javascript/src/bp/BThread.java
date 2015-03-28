package bp;

import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;

import static bp.eventSets.EventSetConstants.none;

import org.mozilla.javascript.*;

import java.io.Serializable;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public abstract class BThread implements Serializable {

    private String name = this.getClass().getSimpleName();
    transient protected BProgram bp = null;
    protected Scriptable _scope;
    protected Script _script;

    ContinuationPending _cont;
    RequestableInterface _request;
    EventSetInterface _wait;
    EventSetInterface _block;

    public BThread() {
        _request = none;
        _wait = none;
        _block = none;
    }

    public ContinuationPending getCont() {
        return _cont;
    }

    public void set_scope(Scriptable _scope) {
        this._scope = _scope;
    }

    public String getClassName() {
        return "bp.BThread";
    }

    public void start() {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            cx.executeScriptWithContinuations(_script, _scope);
        } catch (ContinuationPending pending) {
            _cont = pending;
        } finally {
            Context.exit();
        }
    }

    public ContinuationPending resume(BEvent event) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            cx.resumeContinuation(_cont.getContinuation(), _scope, event);
        } catch (ContinuationPending pending) {
            _cont = pending;
            return _cont;
        } finally {
            Context.exit();
        }
        //unsuccessful resume of bthread
        return null;
    }


    public RequestableInterface getRequestedEvents() {
        return _request;
    }

    public void setRequestedEvents(RequestableInterface requestedEvents) {
        _request = requestedEvents;
    }

    public EventSetInterface getWaitedEvents() {
        return _wait;
    }

    public void setWaitedEvents(EventSetInterface watchedEvents) {
        _wait = watchedEvents;
    }

    public EventSetInterface getBlockedEvents() {
        return _block;
    }

    public void setBlockedEvents(EventSetInterface blockedEvents) {
        _block = blockedEvents;
    }

    /**
     * The set of events that will interrupt this scenario.
     */
    transient protected EventSetInterface interruptingEvents = none;

    public BThread(String name) {
        this.setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see java.lang.Thread#start()
     */

    public boolean isRequested(BEvent event) {
        return (_request.contains(event));
    }

    public String toString() {
        return "[" + bp + ":" + name + "]";
    }

    public BProgram getBProgram() {
        return bp;
    }

    public void setBProgram(BProgram bp) {
        this.bp = bp;
    }

    protected void bplog(String string) {
        System.out.println("[" + this + "]: " + string);
    }

    // The code below makes sure that we get the same hashCode and equals for
    // copies that come from serialization and then deserialization of the same
    // object.

    static int numerator = 0;
    int hash = numerator++;

    @Override
    public int hashCode() {
        return hash;
    }

    public void bsync(RequestableInterface requestedEvents,
                      EventSetInterface waitedEvents,
                      EventSetInterface blockedEvents) {
        setRequestedEvents(requestedEvents);
        setWaitedEvents(waitedEvents);
        setBlockedEvents(blockedEvents);
        bplog("bsynching with " + requestedEvents + ", " + waitedEvents + ", "
                + blockedEvents);
        Context cx = ContextFactory.getGlobal().enterContext();
        try {
            ContinuationPending pending = cx.captureContinuation();
            throw pending;
        } finally {
            Context.exit();
        }
    }

    public void zombie() {
        bplog("going zombie...");
        bsync(none, none, none);
        // requestedEvents = EventSetConstants.none;
        // watchedEvents = EventSetConstants.none;
        // blockedEvents = EventSetConstants.none;
    }
}

