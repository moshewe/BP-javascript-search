package bp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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

    protected abstract void addBThreads();

    protected void start() {
        addBThreads();
        setupScope();
        _bp.start();
    }

    protected void setupScope() {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            _globalScope = cx.initStandardObjects();
            for (BThread bt : _bp.getBThreads()) {
                bt.setScope(_globalScope);
                String btJsName = bt.JSIdentifier();
                bt.setScript(btJsName + ".runBThread();\n");
                _globalScope.put(bt.getName() + bt.hashCode(),
                        _globalScope, Context.javaToJS(bt, _globalScope));
            }
        } finally {
            Context.exit();
        }
    }

}

