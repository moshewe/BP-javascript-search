package bp;

import bp.eventSets.EventSetConstants;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import org.mozilla.javascript.*;

import java.io.Serializable;

import static bp.BProgramControls.debugMode;
import static bp.eventSets.EventSetConstants.none;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public class BThread implements Serializable {

    public Function _func = null;
    protected String _name = this.getClass().getSimpleName();
    transient protected BProgram bp = null;
    protected Scriptable _scope;
    protected Script _script = null;
    ContinuationPending _cont;
    RequestableInterface _request;
    EventSetInterface _wait;
    EventSetInterface _block;
    private boolean _alive = true;


    public BThread() {
        _request = none;
        _wait = none;
        _block = none;
    }

    public BThread(String source) {
        this();
        setScript(source);
    }

//    public BThread(Script script){
//        this();
//        setScript(script);
//    }

    public BThread(String name, String source) {
        this(source);
        _name = name;
    }

    public BThread(Function func) {
        this();
        _func = func;
        setScript("_func();");
    }

    public boolean isAlive() {
        return _alive;
    }

    public ContinuationPending getCont() {
        return _cont;
    }

    public void setCont(ContinuationPending cont) {
        _cont = cont;
    }

    public void setupScope(Scriptable programScope) {
        Context cx = ContextFactory.getGlobal().enterContext();
//        Object btInJS = cx.javaToJS(this, programScope);
//        Scriptable btScope = cx.toObject(btInJS, programScope);
//        Scriptable btScope = cx.toObject(this, programScope);
        Scriptable btScope = (Scriptable) Context.javaToJS(this, programScope);
        btScope.setPrototype(programScope);
//        btScope.setParentScope(null);
        _scope = btScope;
    }

    public void start() {
//        if (_func != null) {
//            _func.setParentScope(_scope);
//
//        }
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
//        if (_scope == null) {
//            bplog("null scope?");
//        }
        try {
            bplog("started!");
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

        bplog(" I'm over!");
        _alive = false;
        zombie();
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

    public boolean isRequested(BEvent event) {
        return _request.contains(event);
    }

    public boolean isWaited(BEvent event) {
        return _wait.contains(event);
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String toString() {
        return "[" + _name + "]";
    }

    public BProgram getBProgram() {
        return bp;
    }

    public void setBProgram(BProgram bp) {
        this.bp = bp;
    }

    protected void bplog(String string) {
        if (debugMode)
            System.out.println(this + ": " + string);
    }

    public BEvent bsync(RequestableInterface requestedEvents,
                        EventSetInterface waitedEvents,
                        EventSetInterface blockedEvents) {
        setRequestedEvents(requestedEvents);
        setWaitedEvents(waitedEvents);
        setBlockedEvents(blockedEvents);
        bplog("bsyncing with " + requestedEvents + ", " +
                waitedEvents + ", " + blockedEvents);
        Context cx = ContextFactory.getGlobal().enterContext();
        try {
            ContinuationPending pending = cx.captureContinuation();
            throw pending;
        } finally {
            Context.exit();
        }
    }

    public void zombie() {
        _request = EventSetConstants.none;
        _wait = EventSetConstants.none;
        _block = EventSetConstants.none;
        _cont = null;
    }

    public Script getScript() {
        return _script;
    }

    public void setScript(String source) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            _script = cx.compileString(source, _name + "-script", 1, null);
        } finally {
            Context.exit();
        }
    }

    public void revive() {
        _alive = true;
    }
}

