package bp;

import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import org.mozilla.javascript.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static bp.eventSets.EventSetConstants.none;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public abstract class BThread implements Serializable {

    protected String _name = this.getClass().getSimpleName();
    private boolean _alive = true;
    transient protected BProgram bp = null;
    protected Scriptable _scope;
    protected Script _script;

    ContinuationPending _cont;
    RequestableInterface _request;
    EventSetInterface _wait;
    EventSetInterface _block;

    protected List<JSIdentifiable> _btScopeObjects;

    public boolean isAlive() {
        return _alive;
    }


    public BThread() {
        _request = none;
        _wait = none;
        _block = none;
        _btScopeObjects = new ArrayList<JSIdentifiable>();
    }

    public ContinuationPending getCont() {
        return _cont;
    }

    public void setScope(Scriptable _scope) {
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
            Object eventInJS = Context.javaToJS(event, _scope);
            cx.resumeContinuation(_cont.getContinuation(), _scope,
                    eventInJS);
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

    public BThread(String _name) {
        this.setName(_name);
        _btScopeObjects = new ArrayList<>();
    }

    public void setName(String name) {
        this._name = name;
    }

    /**
     * @see java.lang.Thread#start()
     */

    public boolean isRequested(BEvent event) {
        return (_request.contains(event));
    }

    public String getName() {
        return _name;
    }

    public String toString() {
        return "[" + bp + ":" + _name + "]";
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

    public void bsync(RequestableInterface requestedEvents,
                      EventSetInterface waitedEvents,
                      EventSetInterface blockedEvents) {
        setRequestedEvents(requestedEvents);
        setWaitedEvents(waitedEvents);
        setBlockedEvents(blockedEvents);
        bplog("bsyncing with " + requestedEvents + ", " + waitedEvents + ", "
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

    public Script getScript() {
        return _script;
    }

    public void setScript(String source) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        source += jsIdentifier() + ".finished();\n";
        try {
            _script = cx.compileString(source, _name + "-script", 1, null);
        } finally {
            Context.exit();
        }
    }

    public String jsIdentifier() {
        return BPJavascriptApplication.toJSIdentifier(_name +
                hashCode());
    }

    /**
     * Used by the JS script wrapper to declare bthread finished.
     */
    public void finished() {
        _alive = false;
    }

    public void setCont(ContinuationPending cont) {
        this._cont = cont;
    }

    protected void setupScope() {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            for (JSIdentifiable obj : _btScopeObjects) {
                _scope.put(obj.jsIdentifier(), _scope,
                        Context.javaToJS(obj, _scope));
            }
        } finally {
            Context.exit();
        }
    }
}

