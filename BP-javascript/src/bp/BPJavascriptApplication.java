package bp;

import org.mozilla.javascript.*;

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
    private String initScript;

    public void evaluateInGlobalScope(String path) {
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

    public void registerBT(Callable func) {
    }

    protected void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + ": " + s);
    }

    public BPJavascriptApplication() {
        _bp = new BProgram();
        setupGlobalScope();
    }

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
            _globalScope.put("bpjs", _globalScope,
                    Context.javaToJS(this, _globalScope));
            _globalScope.put("none", _globalScope,
                    Context.javaToJS(none, _globalScope));
            _globalScope.put("all", _globalScope,
                    Context.javaToJS(all, _globalScope));
        } finally {
            Context.exit();
        }
        initScript = "/Users/orelmosheweinstock/IdeaProjects/BP-javascript-search/BP-javascript/src/bp/globalScopeInit.js";
        evaluateInGlobalScope(initScript);
    }

}


