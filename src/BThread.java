import org.mozilla.javascript.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public class BThread implements Serializable {

    Scriptable _scope;
    ContinuationPending _cont;
    Object _request;
    Object _wait;
    Object _block;
    Script _script;

    public BThread() {
    }

    public void set_scope(Scriptable _scope) {
        this._scope = _scope;
    }

    public String getClassName() {
        return "BThread";
    }

    public RWB bsync(Object request, Object wait, Object block) {
        Context cx = ContextFactory.getGlobal().enterContext();
        try {
            ContinuationPending pending = cx.captureContinuation();
            pending.setApplicationState(new RWB(request, wait, block));
            throw pending;
        } finally {
            Context.exit();
        }
    }

    public void start() {
        Context cx = ContextFactory.getGlobal().enterContext();
        try {
            cx.setOptimizationLevel(-1); // must use interpreter mode
            cx.executeScriptWithContinuations(_script, _scope);
        } catch (ContinuationPending pending) {
            RWB rwb = (RWB) pending.getApplicationState();
            _cont = pending;
            _request = rwb.request;
            _wait = rwb.wait;
            _block = rwb.block;
        } finally {
            Context.exit();
        }
    }

    public void resume(Object event) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            cx.resumeContinuation(_cont.getContinuation(), _scope, event);
        } catch (ContinuationPending pending) {
            RWB rwb = (RWB) pending.getApplicationState();
            _cont = pending;
            _request = rwb.request;
            _wait = rwb.wait;
            _block = rwb.block;
        } finally {
            Context.exit();
        }
    }
}

