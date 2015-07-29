package bp;

import bp.eventSets.EventSetConstants;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import org.mozilla.javascript.*;

import javax.naming.OperationNotSupportedException;
import java.io.InputStream;
import java.io.Serializable;

import static bp.BProgramControls.debugMode;
import static bp.eventSets.EventSetConstants.none;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public class BThread implements Serializable {

    public Function _func = null;
    protected String _name = this.getClass().getSimpleName();
    public Scriptable _scope;
    protected ContinuationPending _cont;
    protected RequestableInterface _request;
    protected EventSetInterface _wait;
    protected EventSetInterface _block;
    protected boolean _alive = true;
    private Context _globalContext;


    public BThread() {
        _request = none;
        _wait = none;
        _block = none;
    }

    private void openGlobalContext() {
        _globalContext = ContextFactory.getGlobal().enterContext();
        _globalContext.setOptimizationLevel(-1); // must use interpreter mode
    }

    public BThread(String name, Function func) {
        this();
        _name = name;
        _func = func;
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
        _name = name;
    }

    public String toString() {
        return "[" + _name + "]";
    }

    public void bplog(String string) {
        if (debugMode)
            System.out.println(this + ": " + string);
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
        _scope = generateBThreadScope(programScope);
        if (_func != null) {
            Scriptable funcScope = _func.getParentScope();
            if (funcScope != programScope) {
                while (funcScope.getParentScope() != programScope) {
                    funcScope = funcScope.getParentScope();
                }
                funcScope.setParentScope(_scope);
            } else {
                _func.setParentScope(_scope);
            }
        }
    }

    protected Scriptable generateBThreadScope(Scriptable programScope) {
        Scriptable tScope;
        InputStream script;
        Scriptable btThisScope = (Scriptable) Context.javaToJS(this,
                programScope);
        btThisScope.setPrototype(programScope);
        script = BThread.class.getResourceAsStream("highlevelidioms/breakupon.js");
        tScope = generateSubScope(btThisScope, script, "breakupon");
        script = BThread.class.getResourceAsStream("highlevelidioms/whileblocking.js");
        tScope = generateSubScope(tScope, script, "whileblocking");
        return tScope;
    }

    public Scriptable generateSubScope(Scriptable scope, InputStream ios,
                                       String scriptName) {
        Scriptable tScope = (Scriptable) BJavascriptProgram.evaluateInGlobalContext(
                scope,
                ios,
                scriptName);
        tScope.setPrototype(scope);
        return tScope;
    }

    public void start() {
        try {
            openGlobalContext();
            bplog("started!");
            _globalContext.callFunctionWithContinuations(_func, _scope,
                    new Object[0]);
        } catch (ContinuationPending pending) {
            _cont = pending;
        } finally {
            closeGlobalContext();
        }
    }

    private void closeGlobalContext() {
        Context.exit();
    }

    public ContinuationPending resume(BEvent event) {
        try {
            openGlobalContext();
            Object eventInJS = Context.javaToJS(event, _scope);
            resumeContinuation(eventInJS);
        } catch (ContinuationPending pending) {
            _cont = pending;
            return _cont;
        } finally {
            closeGlobalContext();
        }

        bplog(" I'm over!");
        _alive = false;
        zombie();
        return null;
    }

    private void resumeContinuation(Object eventInJS) {
        _globalContext.resumeContinuation(_cont.getContinuation(), _scope,
                eventInJS);
    }

    public BEvent bsync(Object obj1, EventSetInterface waitedEvents,
                        EventSetInterface blockedEvents) throws OperationNotSupportedException {
        String explanation = "requestedEvents not of type " +
                "RequestableInterface not supported.";
        throw new OperationNotSupportedException(explanation);
    }

    public BEvent bsync(RequestableInterface requestedEvents,
                        EventSetInterface waitedEvents,
                        EventSetInterface blockedEvents) {
        _request = requestedEvents;
        _wait = waitedEvents;
        _block = blockedEvents;
        bplog("bsyncing with " + requestedEvents + ", " +
                waitedEvents + ", " + blockedEvents);
        try {
            ContinuationPending pending =
                    _globalContext.captureContinuation();
            throw pending;
        } finally {
            closeGlobalContext();
        }
    }

    public void zombie() {
        _request = EventSetConstants.none;
        _wait = EventSetConstants.none;
        _block = EventSetConstants.none;
        _cont = null;
    }

    public void revive() {
        _alive = true;
    }
}

