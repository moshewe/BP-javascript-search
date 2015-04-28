package bp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

/**
 * Created by orelmosheweinstock on 3/28/15.
 */
public abstract class BPJavascriptApplication {

    protected Arbiter arbiter;
    protected Scriptable _globalScope;
    protected BProgram _bp;

//    public static String toJSIdentifier(String str) {
//        try {
//            return Arrays.toString(str.getBytes("UTF-8")).replaceAll("[^A-Za-z0-9]", "_");
//        } catch (UnsupportedEncodingException e) {
//            // UTF-8 is always supported, but this catch is required by compiler
//            return null;
//        }
//    }

    public static String btSource(String path) {
        try {
            return new String(readAllBytes(get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "zombieBsync();";
    }

    public void evaluateInGlobalScope(String path){
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            Path pathObject = get(path);
            cx.evaluateString(_globalScope,
                    new String(readAllBytes(pathObject)),
                    pathObject.getFileName().toString(),
                    1,
                    null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Context.exit();
        }
    }

    protected void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + s);
    }

    public BPJavascriptApplication() {
        _bp = new BProgram();
        setupGlobalScope();
        addBThreads();
        setupBThreadScopes();
    }

    protected abstract void addBThreads();

    protected void setupBThreadScopes() {
        setupBThreadScopes(_bp.getBThreads());
    }

    protected void setupBThreadScopes(Collection<BThread> bthreads) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            for (BThread bt : bthreads) {
                bt.setupScope(_globalScope);
//                bt.registerBTInScope();
//                String btJsName = bt.jsIdentifier();
                if (bt.getScript() == null)
                    bt.setScript("runBThread();\n");
//                bt.setScript(btJsName + ".runBThread();\n");
                _globalScope.put(bt.getName(),
                        _globalScope, Context.javaToJS(bt, _globalScope));
            }
        } finally {
            Context.exit();
        }
    }

    protected void start() {
        _bp.start();
    }

    protected void setupGlobalScope() {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            ImporterTopLevel importer = new ImporterTopLevel(cx);
//            importer.initStandardObjects(cx,false);
//            _globalScope = cx.initStandardObjects();
//            _globalScope = importer;
            _globalScope = cx.initStandardObjects(importer);
            _globalScope.put("none", _globalScope,
                    Context.javaToJS(none, _globalScope));
            _globalScope.put("all", _globalScope,
                    Context.javaToJS(all, _globalScope));
        } finally {
            Context.exit();
        }
        String initScript = "out/production/BP-javascript/bp/globalScopeInit.js";
        evaluateInGlobalScope(initScript);
    }

}

