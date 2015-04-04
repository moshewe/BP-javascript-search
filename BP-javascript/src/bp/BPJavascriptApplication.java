package bp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;

import static bp.eventSets.EventSetConstants.none;

/**
 * Created by orelmosheweinstock on 3/28/15.
 */
public abstract class BPJavascriptApplication {

    public static String toJSIdentifier(String str) {
        try {
            return Arrays.toString(str.getBytes("UTF-8")).replaceAll("[^A-Za-z0-9]", "_");
        } catch (UnsupportedEncodingException e) {
            // UTF-8 is always supported, but this catch is required by compiler
            return null;
        }
    }

    protected Scriptable _globalScope;
    protected BProgram _bp;

    public BPJavascriptApplication() {
        _bp = new BProgram();
        setupGlobalScope();
        addBThreads();
        setupBThreadScopes();
    }

    protected void setupBThreadScopes() {
        setupBThreadScopes(_bp.getBThreads());
    }

    protected void setupBThreadScopes(Collection<BThread> bthreads) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            for (BThread bt : bthreads) {
                bt.setScope(_globalScope);
                bt.setupScope();
                String btJsName = bt.jsIdentifier();
                if (bt.getScript() == null)
                    bt.setScript(btJsName + ".runBThread();\n");
                _globalScope.put(btJsName,
                        _globalScope, Context.javaToJS(bt, _globalScope));
            }
        } finally {
            Context.exit();
        }
    }

    protected abstract void addBThreads();

    protected void start() {
        _bp.start();
    }

    protected void setupGlobalScope() {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            _globalScope = cx.initStandardObjects();
            _globalScope.put("none", _globalScope,
                    Context.javaToJS(none, _globalScope));
        } finally {
            Context.exit();
        }
    }

}

