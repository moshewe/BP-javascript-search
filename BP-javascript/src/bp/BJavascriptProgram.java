package bp;

import org.mozilla.javascript.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

/**
 * Created by orelmosheweinstock on 3/28/15.
 */
public abstract class BJavascriptProgram extends BProgram {

    public static final String GLOBAL_SCOPE_INIT = "BPJavascriptGlobalScopeInit";
    protected Arbiter _arbiter;
    protected Scriptable _globalScope;

    public BJavascriptProgram() {
        _arbiter = new Arbiter();
        setupGlobalScope();
    }

    public static Object evaluateInGlobalContext(Scriptable scope,
                                                 String script,
                                                 String scriptName) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        return cx.evaluateString(scope,
                script,
                scriptName,
                1,
                null);
    }

    public static Object evaluateInGlobalContext(Scriptable scope,
                                                 String path) {
        Path pathObject = get(path);
        try {
            String script = new String(readAllBytes(pathObject));
            return evaluateInGlobalContext(scope, script,
                    pathObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object evaluateInGlobalContext(Scriptable scope,
                                                 InputStream ios,
                                                 String scriptName) {
        InputStreamReader streamReader = new InputStreamReader(ios);
        BufferedReader br = new BufferedReader(streamReader);
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String script = sb.toString();
        return evaluateInGlobalContext(scope, script, scriptName);
    }

    public Object evaluateInGlobalScope(String path) {
        return evaluateInGlobalContext(_globalScope, path);
    }

    public Object evaluateInGlobalScope(InputStream ios,
                                        String scriptname) {
        return evaluateInGlobalContext(_globalScope, ios, scriptname);
    }

    public Object evaluateInGlobalScope(String path,
                                        String scriptname) {
        InputStream ios = getClass().getResourceAsStream(path);
        return evaluateInGlobalScope(ios, scriptname);
    }

    //    used from JS to register bthreads in the application
    public BThread registerBThread(String name, Function func) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            BThread bt = new BThread(name, func);
            add(bt);
            return bt;
        } finally {
            Context.exit();
        }
    }

    protected void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + ": " + s);
    }

    protected void setupBThreadScopes() {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            for (BThread bt : _bthreads) {
//                bplog("settping up " + bt + " scope");
                bt.setupScope(_globalScope);
//                if (bt.getScript() == null)
//                    bt.setScript("runBThread();\n");
//                _globalScope.put(bt.getName(),
//                        _globalScope, Context.javaToJS(bt, _globalScope));
            }
        } finally {
            Context.exit();
        }
    }

    protected void setupGlobalScope() {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            ImporterTopLevel importer = new ImporterTopLevel(cx);
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

        InputStream script =
                BJavascriptProgram.class.getResourceAsStream("globalScopeInit.js");
        evaluateInGlobalScope(script, GLOBAL_SCOPE_INIT);
    }

    protected void putInGlobalScope(String objName, Object obj) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            _globalScope.put(objName, _globalScope,
                    Context.javaToJS(obj, _globalScope));
        } finally {
            Context.exit();
        }
    }
}


